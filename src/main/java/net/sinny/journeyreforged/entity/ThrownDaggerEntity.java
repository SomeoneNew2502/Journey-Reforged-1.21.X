package net.sinny.journeyreforged.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.sinny.journeyreforged.registry.EntityRegistry;
import net.sinny.journeyreforged.registry.ItemRegistry;
import net.sinny.journeyreforged.registry.ModEnchantments;

import java.util.List;

public class ThrownDaggerEntity extends PersistentProjectileEntity {

    private static final TrackedData<ItemStack> DAGGER_STACK = DataTracker.registerData(ThrownDaggerEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Boolean> STUCK = DataTracker.registerData(ThrownDaggerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private boolean hasRicocheted = false;
    private Hand throwingHand = Hand.MAIN_HAND;

    private int repossessionLevel;
    private boolean isReturning = false;
    private int returnDelay = 0;

    public ThrownDaggerEntity(EntityType<? extends ThrownDaggerEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownDaggerEntity(World world, LivingEntity owner, Hand hand, ItemStack daggerStack) {
        super(EntityRegistry.THROWN_DAGGER, owner, world, daggerStack.copy(), null);
        this.getDataTracker().set(DAGGER_STACK, daggerStack.copy());
        this.throwingHand = hand;

        this.repossessionLevel = world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(ModEnchantments.REPOSSESSION_KEY)
                .map(enchantmentEntry -> EnchantmentHelper.getLevel(enchantmentEntry, daggerStack))
                .orElse(0);

        this.pickupType = PickupPermission.ALLOWED;

        if (owner != null) {
            Vec3d forwardDir = owner.getRotationVec(1.0F);
            Vec3d rightDir = new Vec3d(-forwardDir.z, 0, forwardDir.x).normalize();
            double sideOffsetDistance = (hand == Hand.MAIN_HAND) ? 0.4 : -0.4;
            Vec3d sideOffset = rightDir.multiply(sideOffsetDistance);
            Vec3d spawnPos = new Vec3d(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
            spawnPos = spawnPos.add(sideOffset).add(forwardDir.multiply(0.5));
            this.setPosition(spawnPos);
        }
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DAGGER_STACK, ItemStack.EMPTY);
        builder.add(STUCK, false);
    }

    @Override
    public void tick() {
        if (this.isReturning) {
            handleReturnFlight();
            return;
        }
        if (this.returnDelay > 0) {
            this.returnDelay--;
            if (this.returnDelay == 0 && !this.getWorld().isClient()) {
                startReturning();
            }
            return;
        }

        if (this.isStuck()) {
            if (this.age > 1200) { this.discard(); }
            List<PlayerEntity> nearbyPlayers = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(0.2D), player -> true);
            if (!nearbyPlayers.isEmpty()) { this.onPlayerCollision(nearbyPlayers.get(0)); }
            return;
        }
        Vec3d currentPos = this.getPos();
        Vec3d nextPos = currentPos.add(this.getVelocity());
        HitResult finalHitResult = null;
        BlockHitResult blockHitResult = this.getWorld().raycast(new RaycastContext(currentPos, nextPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            nextPos = blockHitResult.getPos();
            finalHitResult = blockHitResult;
        }
        EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(this.getWorld(), this, currentPos, nextPos, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::canHit);
        if (entityHitResult != null) { finalHitResult = entityHitResult; }
        if (finalHitResult != null && finalHitResult.getType() != HitResult.Type.MISS) { this.onCollision(finalHitResult); return; }
        Vec3d velocity = this.getVelocity();
        double newY = velocity.y - 0.05;
        Vec3d newVelocity = new Vec3d(velocity.x, newY, velocity.z).multiply(0.99);
        if (this.isTouchingWater()) { newVelocity = newVelocity.multiply(0.8); }
        this.setVelocity(newVelocity);
        this.updateRotation();
        this.setPosition(this.getX() + newVelocity.x, this.getY() + newVelocity.y, this.getZ() + newVelocity.z);
        this.checkBlockCollision();
    }


    private void handleReturnFlight() {
        Entity owner = this.getOwner();
        if (owner == null || !owner.isAlive()) {
            this.isReturning = false;
            this.setNoClip(false);
            this.getDataTracker().set(STUCK, true);
            this.inGround = true;
            this.setVelocity(Vec3d.ZERO);
            return;
        }

        if (this.getBoundingBox().expand(1.0F).intersects(owner.getBoundingBox())) {
            if (owner instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
                if (player.getInventory().insertStack(this.asItemStack())) {
                    player.sendPickup(this, 1);
                    this.discard();
                    return;
                }
            } else if (owner instanceof PlayerEntity player && player.getAbilities().creativeMode) {
                player.sendPickup(this, 1);
                this.discard();
                return;
            }
        }

        Vec3d targetPos = owner.getEyePos();
        Vec3d currentPos = this.getPos();
        Vec3d directionToPlayer = targetPos.subtract(currentPos).normalize();
        double returnSpeed = 1.0 + (this.repossessionLevel * 0.25);
        Vec3d realVelocity = directionToPlayer.multiply(returnSpeed);

        this.setVelocity(realVelocity.negate());
        this.updateRotation();
        this.setVelocity(realVelocity);
        this.setPosition(this.getX() + this.getVelocity().x, this.getY() + this.getVelocity().y, this.getZ() + this.getVelocity().z);
    }

    private void startReturning() {
        this.isReturning = true;
        this.setNoClip(true);
        this.setDamage(0);
        this.pickupType = PickupPermission.DISALLOWED;
        this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity.getType() == EntityType.ENDERMAN) { this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F); return; }
        this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        if (!this.hasRicocheted) {
            this.hasRicocheted = true;
            LivingEntity owner = this.getOwner() instanceof LivingEntity ? (LivingEntity) this.getOwner() : null;
            ItemStack daggerStack = this.getDaggerStack();
            var enchantments = this.getWorld().getRegistryManager().get(net.minecraft.registry.RegistryKeys.ENCHANTMENT);
            if (entity instanceof LivingEntity livingEntity && this.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                float baseDamage = this.getThrownDamage();
                DamageSource source = getDamageSources().mobProjectile(this, owner);
                float totalDamage = EnchantmentHelper.getDamage(serverWorld, daggerStack, entity, source, baseDamage);
                if (entity.damage(source, totalDamage) && owner instanceof PlayerEntity && !((PlayerEntity) owner).getAbilities().creativeMode) {
                    ItemStack damagedStack = daggerStack.copy();
                    net.minecraft.entity.EquipmentSlot slot = (this.throwingHand == Hand.MAIN_HAND) ? net.minecraft.entity.EquipmentSlot.MAINHAND : net.minecraft.entity.EquipmentSlot.OFFHAND;
                    damagedStack.damage(1, owner, slot);
                    this.getDataTracker().set(DAGGER_STACK, damagedStack);
                }
                var punchEntry = enchantments.getEntry(Enchantments.PUNCH);
                if (punchEntry.isPresent()) {
                    int punchLevel = EnchantmentHelper.getLevel(punchEntry.get(), daggerStack);
                    if (punchLevel > 0) {
                        Vec3d knockbackVec = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(punchLevel * 0.6);
                        livingEntity.addVelocity(knockbackVec.x, 0.1, knockbackVec.z);
                    }
                }
            }

            Vec3d bounceVelocity = this.getVelocity().negate().multiply(0.15);
            bounceVelocity = bounceVelocity.add((this.random.nextDouble() - 0.5) * 0.1, 0.1 + this.random.nextDouble() * 0.1, (this.random.nextDouble() - 0.5) * 0.1);
            this.setVelocity(bounceVelocity);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (this.repossessionLevel > 0) {
            this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.setPosition(blockHitResult.getPos());
            this.returnDelay = 5;
            this.setVelocity(Vec3d.ZERO);
        } else {
            this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.setPosition(blockHitResult.getPos());
            this.getDataTracker().set(STUCK, true);
            this.inGround = true;
            this.setVelocity(Vec3d.ZERO);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isStuck()) {
            super.onPlayerCollision(player);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("DaggerStack", this.getDaggerStack().encode(this.getRegistryManager()));
        nbt.putBoolean("IsStuck", this.isStuck());
        nbt.putBoolean("HasRicocheted", this.hasRicocheted);
        nbt.putInt("RepossessionLevel", this.repossessionLevel);
        nbt.putBoolean("IsReturning", this.isReturning);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("DaggerStack")) {
            ItemStack.fromNbt(this.getRegistryManager(), nbt.get("DaggerStack")).ifPresent(stack -> { this.getDataTracker().set(DAGGER_STACK, stack); });
        }
        this.getDataTracker().set(STUCK, nbt.getBoolean("IsStuck"));
        this.hasRicocheted = nbt.getBoolean("HasRicocheted");
        this.repossessionLevel = nbt.getInt("RepossessionLevel");
        this.isReturning = nbt.getBoolean("IsReturning");
        if (this.isReturning) { this.setNoClip(true); }
    }

    @Override
    protected boolean canHit(Entity entity) { if (this.hasRicocheted) { return false; } return super.canHit(entity); }
    @Override
    public boolean isPushable() { return !this.isStuck(); }
    @Override
    protected ItemStack asItemStack() { return this.getDaggerStack().copy(); }
    @Override
    protected ItemStack getDefaultItemStack() { return null; }
    public boolean isStuck() { return this.getDataTracker().get(STUCK); }
    private float getThrownDamage() {
        net.minecraft.item.Item daggerItem = this.getDaggerStack().getItem();
        if (daggerItem == ItemRegistry.WOODEN_DAGGER) { return 2.0f; }
        else if (daggerItem == ItemRegistry.STONE_DAGGER || daggerItem == ItemRegistry.GOLDEN_DAGGER) { return 2.5f; }
        else if (daggerItem == ItemRegistry.IRON_DAGGER) { return 3.5f; }
        else if (daggerItem == ItemRegistry.DIAMOND_DAGGER) { return 4.0f; }
        else if (daggerItem == ItemRegistry.NETHERITE_DAGGER) { return 5.0f; }
        else if (daggerItem == ItemRegistry.PRISMARINE_DAGGER) { return 5.5f; }
        return 2.0f;
    }
    public ItemStack getDaggerStack() { return this.getDataTracker().get(DAGGER_STACK); }
}