/**
 * @author Jian-Hong
 * @since 2015-06-01
 * Calendar元素的操作和处理
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.calendar = {};
$.extend(mlog.calendar, {
	init: function(obj){
		//處理月曆
	      var date = new Date();
	      var d = date.getDate();
	      var m = date.getMonth();
	      var y = date.getFullYear();
	      $(obj).fullCalendar({
	          //fullcalendar本地化  
	          //timeFormat:{agenda: 'h:mm TT{ - h:mm TT}'}, //默認是{agenda: 'h:mm{ - h:mm}}, 影響的是添加的具體的日程上顯示的時間格式.  
	          monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	          monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	          dayNames: ["週日", "週一", "週二", "週三", "週四", "週五", "週六"],
	          dayNamesShort: ["週日", "週一", "週二", "週三", "週四", "週五", "週六"],
	          today: ["今天"],
	          axisFormat: "HH:mm",
	          //timeFormat: "HH:mm{~HH:mm}",
	          timeFormat: "HH:mm",
	          firstDay: 1,
	          allDaySlot: false,
	          selectable: true,
	          selectHelper: true,
	          weekMode:'5',
	          aspectRatio: 2.7,
	          editable: false,
	          allDayDefault: false, 
	          header: {
	          		left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay'
	          },
	          defaultView: 'month',
	          titleFormat: {
					month:"yyyy MMMM",
					week:"yyyy MMMM dd{'&#8212;'MMMM dd}",
					day:"yyyy dddd M/dd"				  
	          },
	          buttonText: {
	              today: '今天',
	              month: '月',
	              week: '週',
	              day: '日',
	              prev: '上',
	              next: '下'
	          },
	          select: function(start, end, allDay) {
	        	  mlog.calendar.forwardToCreatePage(start,end);
	          },
	          events: function(start, end, timezone, callback) {
	          	  var view = $(obj).fullCalendar('getView');
	          	  var viewName = view.name;
	              var viewStart = moment(view.start).format("YYYY-MM-DD HH:mm:ss");
	              var viewEnd = moment(view.end).format("YYYY-MM-DD HH:mm:ss");
	              $(obj).fullCalendar('removeEvents');
	              if(view!='month') $(obj).fullCalendar('option', 'height', 800);
	              $.getJSON(mlog.variable.base+'/api/employee/jsonCalendar', {
	                  start: viewStart,
	                  end: viewEnd
	              }, function (data) {
	              	  $("#calendarInfo").hide();
	                  if (data == null)
	                      return;
	                  for (var i = 0; i < data.length; i++) {
	                  	  switch(data[i].title){
	                  	  	case "危險期":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#F88017", "icon-smile");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	case "排卵期":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#F88017", "icon-star");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	case "親密接觸":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#FF0000", "icon-heart-empty");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	case "預估經期":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#F778A1", "icon-tint fmc");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	case "服用避孕藥":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#5CB3FF", "icon-plus-sign");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	case "實際經期":
	                  	  		mlog.calendar.renderFemaleEvent(data[i].start, data[i].end, "#8C001A", "icon-tint");
	                  	  		if(viewName=='month') $("#calendarInfo").show();
	                  	  		break;
	                  	  	default:
	                  	  		var event1 = new Object();
	                  	  		event1.id = data[i].id;
			                  	event1.title = data[i].title;
			                  	event1.description = data[i].content.replace(/\r\n/g,'<br>').replace(/"/g,"'");
			                  	event1.backgroundColor = data[i].bgColor;
			                  	event1.textColor = data[i].color;
			                  	event1.color = "#ffffff";
			                  	event1.reminderMinutes = data[i].reminderMinutes;
			                  	event1.messagenotice = data[i].content;
			                  	event1.start = data[i].start;
			                  	event1.end = data[i].end;
	                      		$(obj).fullCalendar('renderEvent', event1, true);
	                  	  }
	                  }
	              }); //把從後台取出的數據進行封裝以後在頁面上以fullCalendar的方式進行顯示 
	          },
		      eventRender: function (event, element) {
		      	element.attr('href', 'javascript:void(0);');
		        element.attr('onclick', 'mlog.calendar.openModal("' + event.id+ '","' +event.title + '","' + event.description + '","' + event.url + '","' + event.reminderMinutes + '","' + moment(event.start).unix() + '","' + moment(event.end).unix() + '");');
		      }
	      }); 
	},

  forwardToCreatePage: function (start,end){
   		var start = moment(start).format('YYYY-MM-DD HH:mm:ss');
        var end = moment(end).subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
        $('#startDate').val(start);
        $('#endDate').val(end);
        $('#listForm').submit();
  },
   
  renderFemaleEvent: function (start, end, color, font){
   		var startDate = mlog.calendar.generateDate(start);
   		var endDate = mlog.calendar.generateDate(end);
   		var date = startDate;
   		while(date<=endDate){
   			if($("#day"+mlog.calendar.calenderDateFormate(date)).find("i[class='"+font+"']").length==0){
   				$("#day"+mlog.calendar.calenderDateFormate(date)).append("<i class='"+font+"' style='color:"+color+"; margin-right:2px;'></i>");
   			}
   			date = mlog.calendar.addDays(date,1);
   		}
  },
   
  calenderDateFormate: function (date){
   		return mlog.calendar.digit2((date.getMonth()+1))+mlog.calendar.digit2((date.getDate()));
  },
  
  generateDate: function (dateStr){
   		var d = new Date(parseInt(dateStr.substring(0,5)), (parseInt(dateStr.substring(5,7))-1), (parseInt(dateStr.substring(8,10))), 0, 0, 0, 0);
   		return d;
  },
  
  addDays: function (date, days) {
    	date.setDate(date.getDate() + days);
    	return date;
  },
  
  digit2: function (n){
		return n > 9 ? "" + n: "0" + n;
  },
  
  openModal: function (id, title, info, url, reminderMinutes,  start, end) {
    if (start) {
        $("#startTime").html("<i class='icon-tag'></i> 開始時間: " + moment.unix(start).format("YYYY-MM-DD HH:mm") + "<br />")
    } else {
        $("#startTime").html(""); //no start (huh?) clear out previous info.
    }
    if (end){
        $("#endTime").html("<i class='icon-tag'></i> 結束時間: " + moment.unix(end).format("YYYY-MM-DD HH:mm") + "<br />")
    } else {
        $("#endTime").html(""); //no end. clear out previous info.
    } 
    $("#eventInfo").html("<br />" + info);
    $("#eventLink").attr('href', 'javascript:void(0);');
    $("#eventLink").attr('onclick', "window.location.href='"+mlog.variable.base+"/user/calendar/edit?id="+id+"'");
    $("#calendarModel").modal('show');
  }
  
});