package com.example.a2340team25game.viewModel;

import android.view.Choreographer;

import com.example.a2340team25game.model.Player;

public class MoveInDirectionStrategy implements MovementStrategy, Observer {
    private char directionMoving;
    private boolean isCollisionTop;
    private boolean isCollisionBottom;
    private boolean isCollisionLeft;
    private boolean isCollisionRight;

    @Override
    public void moveStart(char direction) {
        directionMoving = direction;
        Choreographer.getInstance().postFrameCallback(movementCallback);
    }

    @Override
    public void moveStop() {
        directionMoving = '\0';  // Set directionMoving to '\0' to indicate no movement
        Choreographer.getInstance().removeFrameCallback(movementCallback);
    }

    @Override
    public void update(String collisionStatus) {
        switch (collisionStatus) {
        case "Top Collision":
            isCollisionTop = true;
            break;
        case "No Top Collision":
            isCollisionTop = false;
            break;
        case "Left Collision":
            isCollisionLeft = true;
            break;
        case "No Left Collision":
            isCollisionLeft = false;
            break;
        case "Bottom Collision":
            isCollisionBottom = true;
            break;
        case "No Bottom Collision":
            isCollisionBottom = false;
            break;
        case "Right Collision":
            isCollisionRight = true;
            break;
        case "No Right Collision":
            isCollisionRight = false;
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + collisionStatus);
        }
    }

    private Choreographer.FrameCallback movementCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            if (directionMoving == 'U' && !isCollisionTop && Player.getInstance().getYPos() > 0) {
                Player.getInstance().setYPos(Player.getInstance().getYPos()
                        - Player.getInstance().getMoveSpeed());
            } else if (directionMoving == 'D' && !isCollisionBottom
                    && Player.getInstance().getYPos() < 1800) {
                Player.getInstance().setYPos(Player.getInstance().getYPos()
                        + Player.getInstance().getMoveSpeed());
            } else if (directionMoving == 'L' && !isCollisionLeft
                    && Player.getInstance().getXPos() > 0) {
                Player.getInstance().setXPos(Player.getInstance().getXPos()
                        - Player.getInstance().getMoveSpeed());
            } else if (directionMoving == 'R' && !isCollisionRight
                    && Player.getInstance().getXPos() < 2560) {
                Player.getInstance().setXPos(Player.getInstance().getXPos()
                        + Player.getInstance().getMoveSpeed());
            }
            Choreographer.getInstance().postFrameCallback(this);
        }
    };

    public boolean isCollisionTop() {
        return this.isCollisionTop;
    }

    public boolean isCollisionBottom() {
        return this.isCollisionBottom;
    }

    public boolean isCollisionRight() {
        return this.isCollisionRight;
    }

    public boolean isCollisionLeft() {
        return this.isCollisionLeft;
    }
}
