<template>
  <div class="charts-row">
    <div ref="pieChart" class="chart-container"></div>
    <div ref="barChart" class="chart-container"></div>
  </div>
</template>

<script>
import { getGenderDistribution, getClassDistribution } from '@/api/student-report'
import * as echarts from 'echarts';

export default {
  name: 'ChartsView',
  data() {
    return {
      pieChartInstance: null,
      barChartInstance: null,
      pieChartOptions: {},
      barChartOptions: {}
    };
  },
  mounted() {
    this.pieChartInstance = echarts.init(this.$refs.pieChart);
    this.barChartInstance = echarts.init(this.$refs.barChart);
    this.fetchChartData();
  },
  methods: {
    fetchChartData() {
      getGenderDistribution().then(response => {
        const genderData = response.data.data;
        console.log(genderData);
        // 处理数据并更新饼图
        this.pieChartOptions = {
          title: {
          text: '学员性别分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '性别',
            type: 'pie',
            radius: '50%',
            data: genderData.map(item => ({value: item.count, name: item.gender})),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
        };
        this.updatePieChart();
      }).catch(error => {
        // 处理错误
        console.error('获取性别分布数据错误:', error);
      });

      getClassDistribution().then(response => {
        const classData = response.data.data;
        console.log(classData);
        // 处理数据并更新柱状图
        this.barChartOptions = {
          title: {
            text: '学员班级统计',
            left: 'center'
          },
          tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: classData.map(item => ({ value: item.name }))
        },
        yAxis: {
          type: 'value'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '班级',
            type: 'bar',
            radius: '50%',
            data: classData.map(item => ({ value: item.count, name: item.name })),
            markPoint: {
            data: [
              {type: 'max', name: '最大值'},
              {type: 'min', name: '最小值'}
              ]
            },
          }
        ]
      };
      this.updateBarChart();
    }).catch(error => {
        // 处理错误
        console.error('获取学员班级统计错误:', error);
      });
    },
    // 更新饼图的方法
    updatePieChart() {
      const pieChart = echarts.init(this.$refs.pieChart);
      pieChart.setOption(this.pieChartOptions);
    },

    // 更新柱状图的方法
    updateBarChart() {
      const barChart = echarts.init(this.$refs.barChart);
      barChart.setOption(this.barChartOptions);
    }
  },
  beforeDestroy() {
    // 在组件销毁前，清理图表实例
    if (this.pieChartInstance) {
      this.pieChartInstance.dispose();
    }
    if (this.barChartInstance) {
      this.barChartInstance.dispose();
    }
  },
};
</script>

<style>
.charts-row {
  display: flex;
  justify-content: space-around;
  align-items: center; /* 垂直居中 */
  /* 确保父容器有足够的高度 */
  min-height: 400px; 
  height: 80vh; /* 设置父容器高度为视窗的高度 */
  padding: 20px; /* 或者任何适当的间隔 */
}

.chart-container {
  flex: 1; /* 子项将平均分配空间 */
  min-width: 0; /* 防止flex子项缩到小于其内容的宽度 */
  /* 如果图表不显示，可以尝试给一个固定的高度 */
  height: 400px;
}
</style>
