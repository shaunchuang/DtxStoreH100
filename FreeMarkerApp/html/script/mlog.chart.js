/**
 * @author Jian-Hong
 * chart 2015-06-01
 * Calendar元素的操作和处理
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.chart = {};
$.extend(mlog.chart, {
	init: function (elementID, names, plotBands, url){
		var seriesOptions = [],
	        yAxisOptions = [],
	        seriesCounter = 0,
	        colors = Highcharts.getOptions().colors;
	
	    $.each(names, function (i, name) {
	    	// todo
	    	$.ajax({
				type: "POST",
				url:  url+ '?type=' + name.toLowerCase() + '&callback=?',
				dataType: 'json',
				async: false,
				success: function(data){
					
		            seriesOptions[i] = {
		                	name: name,
		                	data: data
		            };
		            if(data.length==1){
		            	seriesOptions[i].marker = {
							enabled : true,
							radius : 5
						} 
		            }else{
		            	seriesOptions[i].marker = {
							enabled : true,
							radius : 4,
							fillColor: '#FFFFFF',
		                    lineWidth: 2,
		                    lineColor: null // inherit from series
						}
		            }
		
		            // As we're loading the data asynchronously, we don't know what order it will arrive. So
		            // we keep a counter and create the chart when all the data is loaded.
		            seriesCounter++;
		            
		            if (seriesCounter == names.length) {
		                createChart(elementID);
		            }
		            
				}
			});
	    });
	
	    // create the chart when all data is loaded
	    function createChart(elementID) {
	
	        Highcharts.setOptions({
	            lang: {
	                shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	                weekdays: ['週日', '週一', '週二', '週三', '週四', '週五', '週六'],
	                rangeSelectorFrom: '起始',
	                rangeSelectorTo: '終止',
	                rangeSelectorZoom: '縮放'
	            },
	         	global: {
					timezoneOffset: -8 * 60
	        	}
	        });
	
	        $(elementID).highcharts('StockChart', {
	            chart: {
	            	events: {
	             	   load: function() {
	                	    var chart = this;
	                	    
	                	    if(chart.legend.allItems && chart.legend.allItems.length>0){
			                    $.each(chart.legend.allItems, function(i, item){
		    	                    var $check = $(item.checkbox),
		        	                    left = parseFloat($check.css('left')),
		            	                label = item.legendItem,
		                	            static = 50;
		                        
		                    	    $check.css({
		                        	    left: (left - label.bBox.width - static) + 'px'  
		                        	});
		                    	});
	                	    }
	                	}
	            	}
	            },
	            exporting: {    
	                enabled: true //是否能導出趨勢圖圖片  
	            },
	            credits: {
	                enabled: false
	            },
	            tooltip: {
	                dateTimeLabelFormats: '%Y-%m-%d %H:%M:%S',
	                //xDateFormat: '%Y-%m-%d %H:%M:%S' //鼠標移動到趨勢線上時顯示的日期格式
	                xDateFormat: '%Y-%m-%d'
	            },
	            legend: {
	                enabled: false,
	                align: 'center',
	                verticalAlign: 'top',
	                itemMarginTop: 2,
	                padding: 30,
	                itemDistance: 10
	            },
	            xAxis: {
	                /*
	                dateTimeLabelFormats: {
	                    millisecond: '%H:%M:%S.%L',
	                    second: '%H:%M:%S',
	                    minute: '%H:%M',
	                    hour: '%H:%M',
	                    day: '%m/%d',
	                    week: '\'%y/%m',
	                    month: '\'%y/%m',
	                    year: '%Y'
	                }
	                */
	                type: 'datetime',
	            	dateTimeLabelFormats: {
	                	millisecond: '%m/%d',
	                    second: '%m/%d',
	                    minute: '%m/%d',
	                    hour: '%m/%d',
	                    day: '%m/%d',
	                    week: '%m/%d',
	                    month: '%m/%d',
	                    year: '%m/%d'
	            	}
	            },
	            yAxis : {
					plotBands : plotBands.slice(0)
				},
	 			navigator : {
	                                enabled : false
	                        },			
	            rangeSelector: {
	                inputDateFormat: '%Y-%m-%d',
	                selected: 4,
	                buttons: [{
	                    type: 'month',
	                    count: 1,
	                    text: '月'
	                }, {
	                    type: 'month',
	                    count: 3,
	                    text: '季'
	                }, {
	                    type: 'month',
	                    count: 6,
	                    text: '半年'
	                }, {
	                    type: 'ytd',
	                    text: 'YTD'
	                }, {
	                    type: 'year',
	                    count: 1,
	                    text: '一年'
	                }, {
	                    type: 'all',
	                    text: '全部'
	                }]
	            },
	            series: seriesOptions,
	            plotOptions: {
	            	series: {
	            		showCheckbox : true,
	            		selected: true,
	                	events: {
	                    	legendItemClick: function(event) {
	                    		//被點選的曲線名稱
	                    		var clickSeries = this.name;
	                    		//取得所有曲線
	                    		var series = this.yAxis.series;
	                    		var seriesLen = series.length;
	                    		//用來刪除與增加plotBand
	                    		var plotBandID = "";
	                    		//清除所有色帶
	                    		for(var i = 0; i < seriesLen; i++) {
	                    			plotBandID = "plotband-"+(i+1);
	                    			this.yAxis.removePlotBand(plotBandID);
	                    		}
	                    		//重新繪製色帶
	                    		for(var i = 0; i < seriesLen; i++) {
	                    			if(clickSeries==series[i].name && series[i].visible==series[i].selected){
	                    				//check box 與 legend上的item visible同步
	                    				series[i].select();
	                    			}
	                    			if((clickSeries==series[i].name && !series[i].visible)
	                    				|| (clickSeries!=series[i].name && series[i].visible)){
	                    				//加入色帶
	                    				this.yAxis.addPlotBand(plotBands[i]);
	                    			}
	                    		}
	                    	},
	                    	checkboxClick: function (event) {
	                    		//被點選的曲線名稱
	                    		var clickSeries = this.name;
	                    		//取得所有曲線
	                    		var series = this.yAxis.series;
	                    		var seriesLen = series.length;
	                    		for(var i = 0; i < seriesLen; i++) {
	                    			if(clickSeries==series[i].name){
	                    				$(this.yAxis.series[i].legendItem.element).trigger('click');
	                    				this.yAxis.series[i].select();
	                    				break;
	                    			}
	                    		}
	                    	}
	                	}
	            	}
	            }
	        },function(chart){
	        	// apply the date pickers
	            setTimeout(function () {
	                $('input.highcharts-range-selector', $(chart.container).parent()).datepicker({
	                	format:"yyyy-mm-dd",
						language: 'zh-TW'
	                });
	            }, 0);
	        });
	    }
	}
});