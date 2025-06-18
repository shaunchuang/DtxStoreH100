class DialogExtension {
    constructor() {
        // 暫存要顯示的訊息
        this.currentMessage = '';
        // 暫存要顯示的輸入提示
        this.currentPrompt = '';
        // 暫存使用者剛輸入的文字
        this.inputValue = '';
        
        // 創建對話框元素
        this.createDialogElements();
    }

    // 創建對話框和輸入框的 DOM 元素
    createDialogElements() {
        // 創建對話框容器
        this.dialogContainer = document.createElement('div');
        this.dialogContainer.style.position = 'fixed';
        this.dialogContainer.style.top = '0';
        this.dialogContainer.style.left = '0';
        this.dialogContainer.style.width = '100%';
        this.dialogContainer.style.height = '100%';
        this.dialogContainer.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
        this.dialogContainer.style.display = 'none';
        this.dialogContainer.style.justifyContent = 'center';
        this.dialogContainer.style.alignItems = 'center';
        this.dialogContainer.style.zIndex = '9999';
        
        // 創建訊息對話框
        this.messageDialog = document.createElement('div');
        this.messageDialog.style.backgroundColor = 'white';
        this.messageDialog.style.padding = '20px';
        this.messageDialog.style.borderRadius = '10px';
        this.messageDialog.style.boxShadow = '0 0 10px rgba(0, 0, 0, 0.3)';
        this.messageDialog.style.maxWidth = '80%';
        this.messageDialog.style.textAlign = 'center';
        this.messageDialog.style.display = 'none';
        this.messageDialog.style.flexDirection = 'column';
        this.messageDialog.style.gap = '10px';
        
        // 訊息內容
        this.messageContent = document.createElement('p');
        this.messageContent.style.fontSize = '16px';
        this.messageContent.style.margin = '0 0 15px 0';
        
        // 確認按鈕
        this.messageButton = document.createElement('button');
        this.messageButton.innerText = '確定';
        this.messageButton.style.padding = '8px 20px';
        this.messageButton.style.backgroundColor = '#6C91FF';
        this.messageButton.style.color = 'white';
        this.messageButton.style.border = 'none';
        this.messageButton.style.borderRadius = '5px';
        this.messageButton.style.cursor = 'pointer';
        this.messageButton.style.alignSelf = 'center';
        this.messageButton.addEventListener('click', () => this.hideDialog());
        
        // 組裝訊息對話框
        this.messageDialog.appendChild(this.messageContent);
        this.messageDialog.appendChild(this.messageButton);
        
        // 創建輸入對話框
        this.inputDialog = document.createElement('div');
        this.inputDialog.style.backgroundColor = 'white';
        this.inputDialog.style.padding = '20px';
        this.inputDialog.style.borderRadius = '10px';
        this.inputDialog.style.boxShadow = '0 0 10px rgba(0, 0, 0, 0.3)';
        this.inputDialog.style.maxWidth = '80%';
        this.inputDialog.style.textAlign = 'center';
        this.inputDialog.style.display = 'none';
        this.inputDialog.style.flexDirection = 'column';
        this.inputDialog.style.gap = '10px';
        
        // 輸入提示
        this.inputPromptText = document.createElement('p');
        this.inputPromptText.style.fontSize = '16px';
        this.inputPromptText.style.margin = '0 0 15px 0';
        
        // 輸入框
        this.inputField = document.createElement('input');
        this.inputField.type = 'text';
        this.inputField.style.padding = '8px';
        this.inputField.style.borderRadius = '5px';
        this.inputField.style.border = '1px solid #ccc';
        this.inputField.style.fontSize = '14px';
        this.inputField.style.width = '100%';
        this.inputField.style.boxSizing = 'border-box';
        this.inputField.style.marginBottom = '15px';
        
        // 確認按鈕
        this.inputButton = document.createElement('button');
        this.inputButton.innerText = '確定';
        this.inputButton.style.padding = '8px 20px';
        this.inputButton.style.backgroundColor = '#6C91FF';
        this.inputButton.style.color = 'white';
        this.inputButton.style.border = 'none';
        this.inputButton.style.borderRadius = '5px';
        this.inputButton.style.cursor = 'pointer';
        this.inputButton.style.alignSelf = 'center';
        this.inputButton.addEventListener('click', () => {
            this.setUserInput({ VALUE: this.inputField.value });
            this.hideDialog();
        });
        
        // 組裝輸入對話框
        this.inputDialog.appendChild(this.inputPromptText);
        this.inputDialog.appendChild(this.inputField);
        this.inputDialog.appendChild(this.inputButton);
        
        // 將對話框添加到容器中
        this.dialogContainer.appendChild(this.messageDialog);
        this.dialogContainer.appendChild(this.inputDialog);
        
        // 將容器添加到 body
        document.body.appendChild(this.dialogContainer);
    }
    
    // 顯示對話框容器
    showDialog() {
        this.dialogContainer.style.display = 'flex';
    }
    
    // 隱藏對話框容器
    hideDialog() {
        this.dialogContainer.style.display = 'none';
        this.messageDialog.style.display = 'none';
        this.inputDialog.style.display = 'none';
    }
    
    // 顯示訊息對話框
    showMessageDialog(message) {
        this.messageContent.innerText = message;
        this.messageDialog.style.display = 'flex';
        this.showDialog();
    }
    
    // 顯示輸入對話框
    showInputDialog(prompt) {
        this.inputPromptText.innerText = prompt;
        this.inputField.value = '';
        this.inputDialog.style.display = 'flex';
        this.showDialog();
        
        // 自動聚焦到輸入框
        setTimeout(() => this.inputField.focus(), 100);
    }

    getInfo() {
        return {
            id: 'dialog',
            name: '提示視窗',
            color1: '#6C91FF',
            color2: '#4A6CD4',
            blocks: [
                {
                    opcode: 'showMessage',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '顯示提示訊息 [MSG]',
                    arguments: {
                        MSG: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: '你好，勇者！'
                        }
                    }
                },
                {
                    opcode: 'showInputPrompt',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '顯示輸入框 [PROMPT]',
                    arguments: {
                        PROMPT: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: '你的名字是？'
                        }
                    }
                },
                {
                    opcode: 'getCurrentMessage',
                    blockType: Scratch.BlockType.REPORTER,
                    text: '目前提示訊息'
                },
                {
                    opcode: 'getCurrentPrompt',
                    blockType: Scratch.BlockType.REPORTER,
                    text: '目前輸入框提示'
                },
                {
                    opcode: 'setUserInput',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '設定使用者輸入為 [VALUE]',
                    arguments: {
                        VALUE: {
                            type: Scratch.ArgumentType.STRING,
                            defaultValue: ''
                        }
                    }
                },
                {
                    opcode: 'getUserInput',
                    blockType: Scratch.BlockType.REPORTER,
                    text: '使用者輸入內容'
                },
                {
                    opcode: 'clearInput',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '清除使用者輸入'
                },
                {
                    opcode: 'hideAllDialogs',
                    blockType: Scratch.BlockType.COMMAND,
                    text: '隱藏所有對話框'
                }
            ]
        };
    }

    showMessage(args) {
        this.currentMessage = args.MSG;
        // 顯示訊息對話框
        this.showMessageDialog(this.currentMessage);
        // 廣播「SHOW_MESSAGE」，讓 Scratch Sprite 知道該顯示提示
        Scratch.vm.runtime.startHats('event_whenbroadcastreceived', {
            BROADCAST_OPTION: 'SHOW_MESSAGE'
        });
    }

    showInputPrompt(args) {
        this.currentPrompt = args.PROMPT;
        // 顯示輸入對話框
        this.showInputDialog(this.currentPrompt);
        // 廣播「SHOW_INPUT」，讓 Scratch Sprite 知道該顯示輸入框
        Scratch.vm.runtime.startHats('event_whenbroadcastreceived', {
            BROADCAST_OPTION: 'SHOW_INPUT'
        });
    }

    getCurrentMessage() {
        return this.currentMessage;
    }

    getCurrentPrompt() {
        return this.currentPrompt;
    }

    setUserInput(args) {
        this.inputValue = args.VALUE;
    }

    getUserInput() {
        return this.inputValue;
    }

    clearInput() {
        this.inputValue = '';
    }
    
    hideAllDialogs() {
        this.hideDialog();
    }
}

Scratch.extensions.register(new DialogExtension());
