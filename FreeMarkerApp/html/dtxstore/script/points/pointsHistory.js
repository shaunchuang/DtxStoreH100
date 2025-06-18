$(document).ready(function() {
    // 載入積分統計數據
    loadPointsStatistics();
    
    // 載入獲得積分記錄
    loadEarningsData(1);
    
    // 切換到使用記錄標籤時載入數據
    $('#usages-tab').on('shown.bs.tab', function (e) {
        loadUsagesData(1);
    });
});

// 載入積分統計
function loadPointsStatistics() {
    $.ajax({
        url: '/points/api/statistics',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            if (data.success) {
                console.log('積分統計：', data);
                $('#total-earned').text(data.totalEarned || 0);
                $('#total-used').text(data.totalUsed || 0);
                $('#login-streak').text(data.loginStreak || 0);
                
                // 更新當前積分直接從API響應獲取
                if (data.currentPoints !== undefined) {
                    $('#current-points').text(data.currentPoints);
                }
            } else {
                console.error('載入積分統計失敗：', data.message);
            }
        },
        error: function(xhr, status, error) {
            console.error('API錯誤：', error);
        }
    });
}

// 載入獲得積分記錄
function loadEarningsData(page) {
    $.ajax({
        url: '/points/api/allocations',
        type: 'GET',
        data: { page: page, size: 10 },
        dataType: 'json',
        success: function(data) {
            if (data.success) {
                renderEarningsTable(data.allocations);
                renderPagination('earnings-pagination', page, data.totalPages, loadEarningsData);
            } else {
                $('#earnings-table-body').html('<tr><td colspan="6" class="text-center">無法載入數據</td></tr>');
            }
        },
        error: function(xhr, status, error) {
            $('#earnings-table-body').html('<tr><td colspan="6" class="text-center">載入失敗</td></tr>');
        }
    });
}

// 載入使用積分記錄
function loadUsagesData(page) {
    $.ajax({
        url: '/points/api/usages',
        type: 'GET',
        data: { page: page, size: 10 },
        dataType: 'json',
        success: function(data) {
            if (data.success) {
                renderUsagesTable(data.usages);
                renderPagination('usages-pagination', page, data.totalPages, loadUsagesData);
            } else {
                $('#usages-table-body').html('<tr><td colspan="4" class="text-center">無法載入數據</td></tr>');
            }
        },
        error: function(xhr, status, error) {
            $('#usages-table-body').html('<tr><td colspan="4" class="text-center">載入失敗</td></tr>');
        }
    });
}

// 渲染獲得積分表格
function renderEarningsTable(allocations) {
    if (!allocations || allocations.length === 0) {
        $('#earnings-table-body').html('<tr><td colspan="6" class="text-center">沒有獲得積分的記錄</td></tr>');
        return;
    }
    
    let html = '';
    allocations.forEach(function(item) {
        html += '<tr>' +
            '<td>' + formatDate(item.allocatedAt) + '</td>' +
            '<td>' + item.description + '</td>' +
            '<td class="text-success">+' + item.points + '</td>' +
            '<td>' + item.remainingPoints + '</td>' +
            '<td>' + (item.expireAt ? formatDate(item.expireAt) : '永不過期') + '</td>' +
        '</tr>';
    });
    
    $('#earnings-table-body').html(html);
}

// 渲染使用積分表格
function renderUsagesTable(usages) {
    if (!usages || usages.length === 0) {
        $('#usages-table-body').html('<tr><td colspan="4" class="text-center">沒有使用積分的記錄</td></tr>');
        return;
    }
    
    let html = '';
    usages.forEach(function(item) {
        html += '<tr>' +
            '<td>' + formatDate(item.usedAt) + '</td>' +
            '<td>' + item.usageType + '</td>' +
            '<td>' + item.description + '</td>' +
            '<td class="text-danger">-' + item.totalUsed + '</td>' +
        '</tr>';
    });
    
    $('#usages-table-body').html(html);
}

// 渲染分頁控制
function renderPagination(elementId, currentPage, totalPages, callback) {
    if (!totalPages || totalPages <= 1) {
        $('#' + elementId).empty();
        return;
    }
    
    let html = '';
    
    // 上一頁按鈕
    html += '<li class="page-item ' + (currentPage === 1 ? 'disabled' : '') + '">' +
        '<a class="page-link" href="#" onclick="' + (currentPage > 1 ? 'return loadPage(' + (currentPage - 1) + ')' : 'return false') + '">上一頁</a>' +
    '</li>';
    
    // 頁碼按鈕
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);
    
    if (endPage - startPage < 4) {
        startPage = Math.max(1, endPage - 4);
    }
    
    for (let i = startPage; i <= endPage; i++) {
        html += '<li class="page-item ' + (i === currentPage ? 'active' : '') + '">' +
            '<a class="page-link" href="#" onclick="return loadPage(' + i + ')">' + i + '</a>' +
        '</li>';
    }
    
    // 下一頁按鈕
    html += '<li class="page-item ' + (currentPage === totalPages ? 'disabled' : '') + '">' +
        '<a class="page-link" href="#" onclick="' + (currentPage < totalPages ? 'return loadPage(' + (currentPage + 1) + ')' : 'return false') + '">下一頁</a>' +
    '</li>';
    
    $('#' + elementId).html(html);
    
    // 定義換頁函數，設為全局函數
    window.loadPage = function(page) {
        callback(page);
        return false; // 防止默認行為
    };
}

// 格式化日期
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.getFullYear() + '/' + 
           padZero(date.getMonth() + 1) + '/' + 
           padZero(date.getDate()) + ' ' + 
           padZero(date.getHours()) + ':' + 
           padZero(date.getMinutes());
}

// 補零
function padZero(num) {
    return num < 10 ? '0' + num : num;
}
