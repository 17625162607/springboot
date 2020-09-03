var chart = Highcharts.chart('chart4', {
	chart: {
		type: 'spline'
	},
	title: {
		text: '两地年度最高气温',
		style:{"color": "#FFFFFF", "fontSize": "18px"}
	},
	subtitle: {
		//text: '数据来源: WorldClimate.com'
	},
	xAxis: {
		categories: ['2008年', '2009年', '2010年', '2011年', '2012年', '2013年',
					 '2014年', '2015年', '2016年', '2017年', '2018年', '2019年']
	},
	legend:{
		itemStyle:{ "color": "#ffffff", "cursor": "pointer", "fontSize": "12px", "fontWeight": "bold" }
	},
	yAxis: {
		title: {
			text: '温度'
		},
		labels: {
			formatter: function () {
				return this.value + '°';
			}
		}
	},
	tooltip: {
		crosshairs: true,
		shared: true
	},
	plotOptions: {
		spline: {
			marker: {
				radius: 4,
				lineColor: '#000000',
				lineWidth: 1
			}
		}
	},
	series: [{
		name: '北京',
		marker: {
			symbol: 'square'
		},
		data: [27.0, 26.9, 29.5, 34.5, 28.2, 31.5, 35.2, {
			y: 36.5
			
		}, 33.3, 38.3, 33.9, 39.6]
	}, {
		name: '上海',
		marker: {
			symbol: 'diamond'
		},
		data: [{
			y: 33.9
			
		}, 34.2, 35.7, 38.5, 31.9, 35.2, 37.0, 36.6, 34.2, 40.3, 36.6, 34.8]
	}]
});