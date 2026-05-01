<template>
  <div class="screen-page">
    <div class="screen-bg screen-bg-a"></div>
    <div class="screen-bg screen-bg-b"></div>

    <section class="hero-panel">
      <div>
        <div class="eyebrow">Housekeeping Service Command Center</div>
        <h1>家政服务 O2O 数据大屏</h1>
        <p>实时关注用户、服务人员、预约、订单、收入与服务质量，帮助管理员快速掌握平台运营状态。</p>
      </div>
      <div class="hero-side">
        <div class="today">{{ nowDate }}</div>
        <div class="role">当前角色：{{ roleName || '管理员' }}</div>
        <el-button round size="mini" class="refresh-btn" @click="loadDashboard">刷新数据</el-button>
      </div>
    </section>

    <section class="kpi-grid">
      <div class="kpi-card" v-for="item in kpis" :key="item.key">
        <div class="kpi-icon">{{ item.icon }}</div>
        <div>
          <div class="kpi-value">{{ item.value }}</div>
          <div class="kpi-label">{{ item.label }}</div>
        </div>
      </div>
    </section>

    <section class="dashboard-grid">
      <div class="chart-card large">
        <div class="card-head">
          <span>服务类型热度</span>
          <em>按服务信息数量统计</em>
        </div>
        <div id="serviceTypeChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>订单状态分布</span>
          <em>接单/待服务/完成</em>
        </div>
        <div id="orderStatusChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>收入概览</span>
          <em>按项目聚合</em>
        </div>
        <div id="incomeChart" class="chart-box"></div>
      </div>
    </section>

    <section class="bottom-grid">
      <div class="notice-card">
        <div class="card-head">
          <span>最新通知公告</span>
          <em>平台消息</em>
        </div>
        <div class="notice-list">
          <div class="notice-item" v-for="item in newsList" :key="item.id" @click="openNews(item)">
            <div class="notice-title">{{ item.title }}</div>
            <div class="notice-time">{{ item.addtime || '暂无时间' }}</div>
          </div>
          <div v-if="!newsList.length" class="empty-text">暂无通知公告</div>
        </div>
      </div>
      <div class="task-card">
        <div class="card-head">
          <span>运营提醒</span>
          <em>今日重点</em>
        </div>
        <div class="task-list">
          <div class="task-item" v-for="item in reminders" :key="item.title">
            <strong>{{ item.title }}</strong>
            <span>{{ item.desc }}</span>
          </div>
        </div>
      </div>
    </section>

    <el-dialog :visible.sync="newsVisible" title="通知公告" :append-to-body="true" width="60%">
      <div class="dialog-title">{{ newsDetail.title }}</div>
      <div class="ql-snow ql-editor" v-html="newsDetail.content"></div>
    </el-dialog>
  </div>
</template>

<script>
import router from '@/router/router-static'
import * as echarts from 'echarts'

export default {
  data() {
    return {
      nowDate: '',
      roleName: '',
      counts: {
        yonghu: 0,
        fuwurenyuan: 0,
        fuwuxinxi: 0,
        fuwuyuyue: 0,
        fuwudingdan: 0,
        shourumingxi: 0
      },
      serviceTypes: [],
      orderStatus: [],
      incomeRows: [],
      newsList: [],
      newsDetail: {},
      newsVisible: false,
      charts: []
    }
  },
  computed: {
    kpis() {
      return [
        { key: 'user', label: '注册用户', value: this.counts.yonghu, icon: '用' },
        { key: 'staff', label: '服务人员', value: this.counts.fuwurenyuan, icon: '员' },
        { key: 'service', label: '服务项目', value: this.counts.fuwuxinxi, icon: '服' },
        { key: 'reserve', label: '服务预约', value: this.counts.fuwuyuyue, icon: '约' },
        { key: 'order', label: '服务订单', value: this.counts.fuwudingdan, icon: '单' },
        { key: 'income', label: '收入记录', value: this.counts.shourumingxi, icon: '收' }
      ]
    },
    reminders() {
      const pending = this.orderStatus.find(item => item.name === '待服务' || item.name === '待接单')
      return [
        { title: '待处理订单', desc: `当前约 ${pending ? pending.value : 0} 单需要关注接单和上门进度。` },
        { title: '服务供给', desc: `平台已有 ${this.counts.fuwurenyuan} 名服务人员，可根据服务类型合理派单。` },
        { title: '客户体验', desc: '建议优先处理取消预约、售后反馈和低评分服务记录。' }
      ]
    }
  },
  mounted() {
    this.nowDate = this.getCurDate ? this.getCurDate() : new Date().toLocaleDateString()
    this.roleName = this.$storage.get('role') || this.$storage.get('sessionTable')
    this.init()
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    this.charts.forEach(chart => chart && chart.dispose())
  },
  methods: {
    init() {
      if (!this.$storage.get('Token')) {
        router.push({ name: 'login' })
        return
      }
      this.$http({ url: `${this.$storage.get('sessionTable')}/session`, method: 'get' }).then(({ data }) => {
        if (data && data.code !== 0) router.push({ name: 'login' })
      })
      this.loadDashboard()
    },
    safeGet(url, params = {}) {
      return this.$http({ url, method: 'get', params }).then(({ data }) => data && data.code === 0 ? data.data : null).catch(() => null)
    },
    async loadDashboard() {
      const keys = Object.keys(this.counts)
      const countResults = await Promise.all(keys.map(key => this.safeGet(`${key}/count`)))
      keys.forEach((key, index) => { this.counts[key] = Number(countResults[index] || 0) })
      const [types, orders, income, news] = await Promise.all([
        this.safeGet('fuwuxinxi/group/fuwuleixing'),
        this.safeGet('fuwudingdan/group/fuwuzhuangtai'),
        this.safeGet('shourumingxi/value/xiangmumingcheng/shouyi'),
        this.safeGet('news/list', { page: 1, limit: 6, sort: 'addtime', order: 'desc' })
      ])
      this.serviceTypes = this.normalizeGroup(types, 'fuwuleixing')
      this.orderStatus = this.normalizeGroup(orders, 'fuwuzhuangtai')
      this.incomeRows = this.normalizeGroup(income, 'xiangmumingcheng')
      this.newsList = news && news.list ? news.list : []
      this.$nextTick(this.renderCharts)
    },
    normalizeGroup(rows, nameKey) {
      return (rows || []).map(item => ({
        name: item[nameKey] || '未分类',
        value: Number(item.total || item.value || 0)
      })).filter(item => item.value >= 0).slice(0, 8)
    },
    renderCharts() {
      this.charts.forEach(chart => chart && chart.dispose())
      this.charts = []
      this.renderServiceTypeChart()
      this.renderOrderStatusChart()
      this.renderIncomeChart()
    },
    createChart(id) {
      const el = document.getElementById(id)
      if (!el) return null
      const chart = echarts.init(el)
      this.charts.push(chart)
      return chart
    },
    renderServiceTypeChart() {
      const chart = this.createChart('serviceTypeChart')
      if (!chart) return
      chart.setOption({
        color: ['#ff6f9f', '#ffb36b', '#7bdcb5', '#7aa7ff', '#ffd166'],
        tooltip: { trigger: 'item' },
        series: [{ type: 'pie', radius: ['42%', '68%'], center: ['50%', '55%'], data: this.serviceTypes.length ? this.serviceTypes : [{ name: '暂无数据', value: 1 }] }]
      })
    },
    renderOrderStatusChart() {
      const chart = this.createChart('orderStatusChart')
      if (!chart) return
      chart.setOption({
        color: ['#ff6f9f'],
        tooltip: { trigger: 'axis' },
        grid: { left: 38, right: 20, top: 25, bottom: 45 },
        xAxis: { type: 'category', data: this.orderStatus.map(i => i.name), axisLabel: { color: '#7d4a5c' } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [{ type: 'bar', barWidth: 22, data: this.orderStatus.map(i => i.value), itemStyle: { borderRadius: [10, 10, 0, 0] } }]
      })
    },
    renderIncomeChart() {
      const chart = this.createChart('incomeChart')
      if (!chart) return
      chart.setOption({
        color: ['#ff9ab9'],
        tooltip: { trigger: 'axis' },
        grid: { left: 45, right: 18, top: 25, bottom: 55 },
        xAxis: { type: 'category', data: this.incomeRows.map(i => i.name), axisLabel: { color: '#7d4a5c', rotate: 25 } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [{ type: 'line', smooth: true, areaStyle: { opacity: 0.18 }, data: this.incomeRows.map(i => i.value) }]
      })
    },
    resizeCharts() {
      this.charts.forEach(chart => chart && chart.resize())
    },
    openNews(row) {
      this.newsDetail = row
      this.newsVisible = true
    }
  }
}
</script>

<style lang="scss" scoped>
.screen-page { min-height: calc(100vh - 84px); position: relative; overflow: hidden; padding: 26px; background: linear-gradient(135deg, #fff1f5 0%, #ffe5ee 46%, #fffaf7 100%); color: #503642; }
.screen-bg { position: absolute; border-radius: 50%; filter: blur(4px); opacity: .48; pointer-events: none; }
.screen-bg-a { width: 360px; height: 360px; right: -120px; top: -100px; background: #ff9ab9; }
.screen-bg-b { width: 260px; height: 260px; left: 30%; bottom: -120px; background: #ffd5a8; }
.hero-panel, .kpi-card, .chart-card, .notice-card, .task-card { position: relative; background: rgba(255,255,255,.78); border: 1px solid rgba(255,111,159,.16); box-shadow: 0 18px 42px rgba(214,73,116,.13); backdrop-filter: blur(10px); }
.hero-panel { display: flex; justify-content: space-between; align-items: flex-end; padding: 34px; border-radius: 28px; }
.eyebrow { color: #d94f7b; font-size: 13px; letter-spacing: 2px; text-transform: uppercase; }
.hero-side { text-align: right; color: #7d4a5c; }
.today { font-size: 22px; font-weight: 700; }
.role { margin: 8px 0 14px; }
.refresh-btn { border: 0; color: #fff; background: #ff6f9f; }
.kpi-grid { display: grid; grid-template-columns: repeat(6, minmax(120px, 1fr)); gap: 18px; margin: 22px 0; }
.kpi-card { display: flex; gap: 14px; align-items: center; padding: 20px; border-radius: 22px; }
.kpi-icon { width: 46px; height: 46px; border-radius: 16px; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #ff6f9f, #ffb2c8); color: #fff; font-weight: 700; }
.kpi-value { font-size: 28px; font-weight: 800; color: #4c2d38; }
.kpi-label { color: #9b6074; font-size: 13px; }
.dashboard-grid { display: grid; grid-template-columns: 1.4fr 1fr 1fr; gap: 18px; }
.chart-card, .notice-card, .task-card { min-height: 330px; padding: 20px; border-radius: 24px; }
.card-head { display: flex; justify-content: space-between; align-items: baseline; margin-bottom: 12px; }
.card-head span { color: #4c2d38; font-size: 18px; font-weight: 800; }
.card-head em { color: #b37a8d; font-style: normal; font-size: 12px; }
.chart-box { width: 100%; height: 270px; }
.bottom-grid { display: grid; grid-template-columns: 1.2fr .8fr; gap: 18px; margin-top: 18px; }
.notice-item, .task-item { padding: 14px 0; border-bottom: 1px solid #ffe0ea; }
.notice-title { color: #51313c; font-weight: 700; cursor: pointer; }
.notice-time, .task-item span { display: block; margin-top: 6px; color: #a77586; font-size: 13px; }
.task-item strong { color: #d94f7b; }
.empty-text { padding: 30px 0; color: #b37a8d; text-align: center; }
.dialog-title { width: 100%; text-align: center; font-size: 20px; color: #000; padding: 0 0 20px; font-weight: bold; }
@media (max-width: 1200px) { .kpi-grid { grid-template-columns: repeat(3, 1fr); } .dashboard-grid, .bottom-grid { grid-template-columns: 1fr; } }
</style>
