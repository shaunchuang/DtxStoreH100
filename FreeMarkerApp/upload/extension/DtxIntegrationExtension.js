class DtxIntegrationExtension {
    constructor() {
        // Initialize the data storage, token, and other parameters
        this.statistics = {};
        this.achievements = {};
        this.jwtToken = null;
        this.planLessonId = null;
        this.startTime = null;
        this.endTime = null;
        this.lastResponse = null;
    }

    getInfo() {
        return {
            id: 'dtxIntegration',
            name: 'DTx平台功能',
            blocks: [
                { opcode: 'addKeyValue', blockType: Scratch.BlockType.COMMAND, text: '新增資料 [KEY] = [VALUE]', arguments: {
                        KEY: { type: Scratch.ArgumentType.STRING, defaultValue: '統計名稱' },
                        VALUE: { type: Scratch.ArgumentType.STRING, defaultValue: '預設統計值' }
                    }
                },
                { opcode: 'increaseValue', blockType: Scratch.BlockType.COMMAND, text: '增加統計值 [KEY] 增加 [AMOUNT]', arguments: {
                        KEY: { type: Scratch.ArgumentType.STRING, defaultValue: '統計名稱' },
                        AMOUNT: { type: Scratch.ArgumentType.NUMBER, defaultValue: 1 }
                    }
                },
                { opcode: 'decreaseValue', blockType: Scratch.BlockType.COMMAND, text: '減少統計值 [KEY] 減少 [AMOUNT]', arguments: {
                        KEY: { type: Scratch.ArgumentType.STRING, defaultValue: '統計名稱' },
                        AMOUNT: { type: Scratch.ArgumentType.NUMBER, defaultValue: 1 }
                    }
                },
                { opcode: 'unlockAchievement', blockType: Scratch.BlockType.COMMAND, text: '解鎖成就 [ACHIEVEMENT_NAME]', arguments: {
                        ACHIEVEMENT_NAME: { type: Scratch.ArgumentType.STRING, defaultValue: '成就名稱' }
                    }
                },
                { opcode: 'clearData', blockType: Scratch.BlockType.COMMAND, text: '清除所有資料' },
                { opcode: 'getDataAsString', blockType: Scratch.BlockType.REPORTER, text: '取得資料列表' },
                { opcode: 'startSession', blockType: Scratch.BlockType.COMMAND, text: '使用者驗證並開始紀錄時間' },
                { opcode: 'sendDataToAPI', blockType: Scratch.BlockType.COMMAND, text: '傳送資料到API' },
                { opcode: 'getLastAPIResponse', blockType: Scratch.BlockType.REPORTER, text: '取得最後一次API回應' }
            ]
        };
    }

    addKeyValue(args) {
        const key = args.KEY.toString();
        const value = args.VALUE.toString();
        this.statistics[key] = value;
    }

    clearData() {
        this.statistics = {};
        this.achievements = {};
    }

    increaseValue(args) {
        const key = args.KEY.toString();
        const amount = Number(args.AMOUNT);
        const currentValue = Number(this.statistics[key]) || 0;
        this.statistics[key] = (currentValue + amount).toString();
    }

    decreaseValue(args) {
        const key = args.KEY.toString();
        const amount = Number(args.AMOUNT);
        const currentValue = Number(this.statistics[key]) || 0;
        this.statistics[key] = (currentValue - amount).toString();
    }

    unlockAchievement(args) {
        const achievementName = args.ACHIEVEMENT_NAME.toString();
        this.achievements[achievementName] = new Date().toISOString();
    }

    getDataAsString() {
        // 將 statistics 與 achievements 轉成列表
        const statisticsList = Object.entries(this.statistics).map(([key, value]) => ({ key, value }));
        const achievementsList = Object.entries(this.achievements).map(([key, value]) => ({ key, value }));
        return JSON.stringify({ statistics: statisticsList, achievements: achievementsList });
    }

    startSession() {
        this.startTime = new Date().toISOString();
    }

    sendDataToAPI() {
        return new Promise(resolve => {
            this.endTime = new Date().toISOString();

            const achievementData = Object.entries(this.achievements).map(([key, value]) => ({ key, value }));
            const statisticsData = Object.entries(this.statistics).map(([key, value]) => ({ key, value }));

            const payload = {
                achievement: achievementData,
                statistics: statisticsData,
                planLessonId: this.planLessonId || 'unknown',
                startTime: this.startTime || 'not_set',
                endTime: this.endTime
            };

            fetch('http://127.0.0.1:7001/TrainingRecord/api/scratchUpload', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': this.jwtToken ? `Bearer ${this.jwtToken}` : ''
                },
                body: JSON.stringify(payload)
            })
            .then(response => response.json())
            .then(json => {
                this.lastResponse = JSON.stringify(json);
                resolve();
            })
            .catch(error => {
                this.lastResponse = `錯誤: ${error.message}`;
                resolve();
            });
        });
    }

    getLastAPIResponse() {
        return this.lastResponse || '尚未有回應';
    }
}

Scratch.extensions.register(new DtxIntegrationExtension());
