
/*** 分析圖表區塊 ***/
const ctx = document.getElementById('stat-chart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['2月', '3月', '4月', '5月', '6月', '7月'],
        datasets: [
            {
                label: 'Minecraft',
                data: [8, 12, 10, 15, 8, 10],
                backgroundColor: '#00CB96',
            },
            {
                label: 'Portal2',
                data: [32, 28, 30, 25, 12, 30],
                backgroundColor: '#3253FF',
            }
        ]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            tooltip: {
                callbacks: {
                    label: function(context) {
                        let label = context.dataset.label || '';
                        if (label) {
                            label += ' 已配發 ';
                        }
                        label += context.parsed.y + '次';
                        return label;
                    },
                    footer: function(context) {
                        let total = context.reduce((sum, item) => sum + item.parsed.y, 0);
                        return '教案總共已配發' + total + '次';
                    }
                }
            }
        },
        scales: {
            x: {
                beginAtZero: true,
            },
            y: {
                beginAtZero: true,
                ticks: {
                    stepSize: 10
                }
            }
        }
    }
});


/*** 排行榜區塊 ***/
document.querySelectorAll('.lb-tab').forEach(tab => {
    tab.addEventListener('click', function() {
        document.querySelectorAll('.lb-tab').forEach(t => t.classList.remove('active'));
        this.classList.add('active');
    });
});
