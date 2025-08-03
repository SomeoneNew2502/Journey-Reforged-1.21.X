package net.sinny.journeyreforged.debug.xp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class XpTrackingManager {

    public static final XpTrackingManager INSTANCE = new XpTrackingManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path SAVE_PATH = FabricLoader.getInstance().getGameDir().resolve("xp-tracker");

    // In-memory data storage
    private final Map<UUID, String> playerNames;
    private final Map<XpSourceCategory, Map<Identifier, AtomicInteger>> globalXpData;
    private final Map<UUID, Map<XpSourceCategory, Map<Identifier, AtomicInteger>>> playerXpData;

    private XpTrackingManager() {
        this.playerNames = new ConcurrentHashMap<>();
        this.globalXpData = new ConcurrentHashMap<>();
        this.playerXpData = new ConcurrentHashMap<>();
    }

    public void addXp(PlayerEntity player, XpSourceCategory category, Identifier specificSource, int amount) {
        if (player == null || amount <= 0) {
            return;
        }

        UUID playerUuid = player.getUuid();
        String playerName = player.getGameProfile().getName();

        // Store player name for the final report
        this.playerNames.putIfAbsent(playerUuid, playerName);

        // Update global data
        this.globalXpData
                .computeIfAbsent(category, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(specificSource, k -> new AtomicInteger(0))
                .addAndGet(amount);

        // Update per-player data
        this.playerXpData
                .computeIfAbsent(playerUuid, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(category, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(specificSource, k -> new AtomicInteger(0))
                .addAndGet(amount);

        // Keep the real-time console log for immediate feedback
        System.out.printf(
                "[XP TRACKER] Player: %s | Category: %s | Source: %s | Amount: %d%n",
                playerName,
                category.name(),
                specificSource.toString(),
                amount
        );
    }

    public void saveDataToFile() {
        if (globalXpData.isEmpty()) {
            System.out.println("[XP TRACKER] No XP data to save.");
            return;
        }

        try {
            Files.createDirectories(SAVE_PATH);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            Path filePath = SAVE_PATH.resolve("xp-report-" + timestamp + ".json");

            SaveData saveData = new SaveData(
                    convertMap(globalXpData),
                    playerXpData.entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> playerNames.getOrDefault(entry.getKey(), entry.getKey().toString()),
                                    entry -> convertMap(entry.getValue())
                            ))
            );

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                GSON.toJson(saveData, writer);
                System.out.println("[XP TRACKER] Successfully saved XP report to: " + filePath);
            }

        } catch (IOException e) {
            System.err.println("[XP TRACKER] Failed to save XP report!");
            e.printStackTrace();
        }
    }

    public void resetData() {
        this.globalXpData.clear();
        this.playerXpData.clear();
        this.playerNames.clear();
        System.out.println("[XP TRACKER] Data has been reset for new session.");
    }

    // Helper to convert the internal AtomicInteger map to a simple map for JSON serialization
    private Map<XpSourceCategory, Map<Identifier, Integer>> convertMap(Map<XpSourceCategory, Map<Identifier, AtomicInteger>> original) {
        return original.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, subEntry -> subEntry.getValue().get()))
                ));
    }

    // A simple record to structure our final JSON file
    private record SaveData(
            Map<XpSourceCategory, Map<Identifier, Integer>> global,
            Map<String, Map<XpSourceCategory, Map<Identifier, Integer>>> players
    ) {}
}