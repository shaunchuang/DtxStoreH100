class MovementExtension {
    getInfo() {
        return {
            id: 'movement',
            name: '角色移動',
            color1: '#FF5733', // 工具欄主要顏色
            color2: '#C70039', // 積木次要顏色
            blocks: [
                {
                    opcode: 'moveUp',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '往上移動 [SPRITE]',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'moveDown',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '往下移動 [SPRITE]',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'moveLeft',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '往左移動 [SPRITE]',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'moveRight',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '往右移動 [SPRITE]',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'jump',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '跳躍 [SPRITE]',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'isTouchingEdge',
                    blockType: Scratch.BlockType.BOOLEAN,
                    text: '[SPRITE] 是否碰到邊緣？',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'nextCostume',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '讓 [SPRITE] 換到下一個造型',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                },
                {
                    opcode: 'shake',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '讓 [SPRITE] 震動一下',
                    arguments: {
                        SPRITE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: 'Sprite1'
                        }
                    }
                }
            ]
        };
    }

    _getTarget(spriteName) {
        const runtime = Scratch.vm.runtime;
        // Check if spriteName is empty or '_stage_', then return the stage
        if (!spriteName || spriteName === '_stage_') {
            return runtime.getTargetForStage();
        }
        // Otherwise find by name (case-insensitive to be more user-friendly)
        return runtime.targets.find(t =>
            t.sprite && t.getName().toLowerCase() === spriteName.toLowerCase());
    }

    moveUp(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            target.setXY(target.x, target.y + 10);
        }
    }

    moveDown(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            target.setXY(target.x, target.y - 10);
        }
    }

    moveLeft(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            target.setXY(target.x - 10, target.y);
        }
    }

    moveRight(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            target.setXY(target.x + 10, target.y);
        }
    }

    jump(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            // 簡化跳躍：直接上升一點
            const initialY = target.y;
            target.setXY(target.x, initialY + 50);
            setTimeout(() => {
                target.setXY(target.x, initialY);
            }, 300); // 模擬下落
        }
    }

    isTouchingEdge(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            const stageWidth = 480;  // Scratch 舞台寬度的一半
            const stageHeight = 360; // Scratch 舞台高度的一半
            return (
                target.x <= -stageWidth || target.x >= stageWidth ||
                target.y <= -stageHeight || target.y >= stageHeight
            );
        }
        return false;
    }

    nextCostume(args) {
        const target = this._getTarget(args.SPRITE);
        if (target && target.sprite && target.sprite.costumes) {
            const costumeCount = target.sprite.costumes.length;
            if (costumeCount > 0) {
                target.setCostume(
                    (target.currentCostume + 1) % costumeCount
                );
            }
        }
    }

    shake(args) {
        const target = this._getTarget(args.SPRITE);
        if (target) {
            const originalX = target.x;
            let count = 0;
            const interval = setInterval(() => {
                target.setXY(originalX + (count % 2 === 0 ? 5 : -5), target.y);
                count++;
                if (count >= 6) {
                    clearInterval(interval);
                    target.setXY(originalX, target.y);
                }
            }, 50);
        }
    }
}

Scratch.extensions.register(new MovementExtension());
