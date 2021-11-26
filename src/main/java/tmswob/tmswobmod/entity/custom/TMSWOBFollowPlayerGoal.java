package tmswob.tmswobmod.entity.custom;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import org.lwjgl.system.CallbackI;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class TMSWOBFollowPlayerGoal extends Goal {
    private final BeanEntity beanEntity;
    private PlayerEntity playerEntity;
    private final float stopDistance = 2;

    public TMSWOBFollowPlayerGoal(BeanEntity beanEntity) {
        this.beanEntity = beanEntity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.findPlayer();
        return this.playerEntity != null && this.playerEntity.isAlive();
    }

    @Override
    public void tick() {
        if (!playerEntity.isCreative() && !playerEntity.isSpectator()){
            this.beanEntity.getLookControl().setLookAt(this.playerEntity, 10.0f, 10.0f);
            if (this.beanEntity.distanceTo(playerEntity) > stopDistance) {
                this.beanEntity.getNavigation().moveTo(playerEntity, 0.4f);
            }
        }
        super.tick();
    }

    private void findPlayer(){
        List<PlayerEntity> playerEntityList = this.beanEntity.level.getEntitiesOfClass(PlayerEntity.class, this.beanEntity.getBoundingBox().inflate(10), playerEntity -> !playerEntity.isCreative() && !playerEntity.isSpectator());
        if (playerEntityList.size() > 0){
            this.playerEntity = playerEntityList.stream().min(Comparator.comparing(this.beanEntity::distanceTo)).get();
        }
    }
}
