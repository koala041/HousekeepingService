<template>
  <div class="screen-page">
    <div class="screen-orb orb-a"></div>
    <div class="screen-orb orb-b"></div>
    <div class="screen-orb orb-c"></div>

    <section class="hero-panel">
      <div class="hero-copy">
        <div class="eyebrow">Housekeeping Service Command Center</div>
        <h1>家政服务运营首页</h1>
        <p>实时关注用户、服务人员、预约、订单、收入与服务质量，帮助管理员快速掌握平台运营状态。</p>
      </div>
      <div class="hero-side">
        <div class="today">{{ nowDate }}</div>
        <div class="role">当前角色：{{ roleName || '管理员' }}</div>
        <el-button round size="mini" class="refresh-btn" @click="loadDashboard">刷新数据</el-button>
      </div>
    </section>

    <section class="notice-ticker">
      <div class="ticker-badge">最新通知</div>
      <div class="ticker-window">
        <div class="ticker-track" :style="tickerStyle">
          <div class="ticker-item" v-for="(item, index) in tickerItems" :key="`${item.id || item.title}-${index}`" @click="openNews(item)">
            <span>{{ item.title }}</span>
            <em>{{ item.addtime || '平台通知' }}</em>
          </div>
        </div>
      </div>
      <div class="ticker-spark"></div>
    </section>

    <section class="kpi-grid">
      <div class="kpi-card" :class="{ 'income-card': item.key === 'income' }" v-for="item in kpis" :key="item.key">
        <div class="kpi-icon"><img :src="item.icon" :alt="item.label" /></div>
        <div>
          <div class="kpi-value">{{ item.value }}</div>
          <div class="kpi-label">{{ item.label }}</div>
        </div>
        <small>{{ item.trend }}</small>
      </div>
    </section>

    <section class="dashboard-grid main-grid">
      <div class="chart-card">
        <div class="card-head">
          <span>服务类型热度</span>
          <em>服务项目结构</em>
        </div>
        <div id="serviceTypeChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>用户男女比例</span>
          <em>注册用户性别</em>
        </div>
        <div id="userGenderChart" class="chart-box"></div>
      </div>
    </section>

    <section class="dashboard-grid main-grid">
      <div class="chart-card">
        <div class="card-head">
          <span>预约时段统计</span>
          <em>服务订单预约时段</em>
        </div>
        <div id="appointmentPeriodChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>预约服务偏好</span>
          <em>预约类型占比</em>
        </div>
        <div id="reserveTypeChart" class="chart-box"></div>
      </div>
    </section>

    <section class="dashboard-grid main-grid">
      <div class="chart-card">
        <div class="card-head">
          <span>各服务类型累计交易</span>
          <em>按服务类型聚合收益</em>
        </div>
        <div id="typeIncomeChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>员工收益Top10</span>
          <em>按收益金额排序</em>
        </div>
        <div id="staffIncomeChart" class="chart-box"></div>
      </div>
    </section>

    <section class="bottom-grid">
      <div class="chart-card">
        <div class="card-head">
          <span>服务质量雷达</span>
          <em>完成、结算、评分综合</em>
        </div>
        <div id="qualityChart" class="chart-box"></div>
      </div>
      <div class="chart-card">
        <div class="card-head">
          <span>账号状态统计</span>
          <em>0 正常 / 1 锁定</em>
        </div>
        <div id="accountStatusChart" class="chart-box"></div>
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
        shourumingxi: 0,
        fuwuqueren: 0
      },
      serviceTypes: [],
      appointmentPeriods: [],
      serviceStatus: [],
      reserveTypes: [],
      userStatus: [],
      staffStatus: [],
      typeIncomeRows: [],
      userGender: [],
      confirmRows: [],
      newsList: [],
      newsDetail: {},
      newsVisible: false,
      charts: [],
      staffIncomeTop10: [],
      platformIncome: 0,
      staffIncome: 0,
      totalTransactionAmount: 0
    }
  },
  computed: {
    kpis() {
      return [
        { key: 'user', label: '注册用户', value: this.counts.yonghu, icon: '/systemPhotos/RegisterNumber1.png'},
        { key: 'staff', label: '服务人员', value: this.counts.fuwurenyuan, icon: '/systemPhotos/ServiceNumber1.png'},
        { key: 'service', label: '服务项目', value: this.counts.fuwuxinxi, icon: '/systemPhotos/ServiceItem1.png'},
        { key: 'reserve', label: '服务预约', value: this.counts.fuwuyuyue, icon: '/systemPhotos/ServiceAppointment1.png'},
        { key: 'order', label: '服务订单', value: this.counts.fuwudingdan, icon: '/systemPhotos/ServiceOrder1.png'},
        { key: 'income', label: '累计交易', value: this.totalTransactionText, icon: '/systemPhotos/TotalMoney1.png'}
      ]
    },
    totalTransactionText() {
      return this.totalTransactionAmount ? `￥${this.formatNumber(this.totalTransactionAmount)}` : '待统计'
    },
    tickerNews() {
      return this.newsList.length ? this.newsList : [{ id: 'empty', title: '暂无通知公告，平台运行正常', addtime: '' }]
    },
    tickerItems() {
      return this.tickerNews.length > 1 ? this.tickerNews.concat(this.tickerNews) : this.tickerNews
    },
    tickerStyle() {
      return { animationDuration: `${Math.max(this.tickerNews.length * 4, 12)}s` }
    },
    reminders() {
      const lockedUsers = this.getGroupValue(this.userStatus, ['锁定'])
      const lockedStaff = this.getGroupValue(this.staffStatus, ['锁定'])
      const pending = this.getGroupValue(this.serviceStatus, ['待服务', '待接单', '未开始'])
      const pendingAccept = this.getGroupValue(this.serviceStatus, ['待接单'])
      const pendingService = this.getGroupValue(this.serviceStatus, ['待服务'])
      return [
        { title: '待处理订单', desc: `当前有 ${pending} 份待处理订单，其中 ${pendingAccept} 份待接订单，${pendingService} 份待服务订单，需要关注接单和上门进度。` },
        { title: '账号风险', desc: `用户锁定 ${lockedUsers} 个，服务人员锁定 ${lockedStaff} 个，请及时核对原因。` },
        { title: '累计交易', desc: `各服务类型累计交易 ￥${this.formatFullNumber(this.totalTransactionAmount)}，其中平台纯收入 ￥${this.formatFullNumber(this.platformIncome)}，重点关注高收益服务品类。` },
        { title: '客户体验', desc: '优先处理取消预约、售后反馈、低评分服务和超时未接单记录。' }
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
      const countResults = await Promise.all(keys.map(key => this.safeGet(`${key}/list`, { page: 1, limit: 1 })))
      keys.forEach((key, index) => {
        const result = countResults[index] || {}
        this.counts[key] = Number(result.total || 0)
      })
      const [types, periods, statusRows, reserves, userStatus, staffStatus, typeIncome, userGender, confirms, news, staffIncome, caiwujiesuan] = await Promise.all([
        this.safeGet('fuwujilu/group/fuwuleixing'),
        this.safeGet('fuwuyuyue/group/yuyueshiduan'),
        this.safeGet('fuwudingdan/group/fuwuzhuangtai'),
        this.safeGet('fuwuyuyue/group/fuwuleixing'),
        this.safeGet('yonghu/group/status'),
        this.safeGet('fuwurenyuan/group/status'),
        this.safeGet('shourumingxi/value/fuwuleixing/shouyi'),
        this.safeGet('yonghu/group/xingbie'),
        this.safeGet('fuwuqueren/list', { page: 1, limit: 20000, sort: 'addtime', order: 'desc' }),
        this.safeGet('news/list', { page: 1, limit: 8, sort: 'addtime', order: 'desc' }),
        this.safeGet('shourumingxi/value/yuangongxingming/shouyi'),
        this.safeGet('caiwujiesuan/list', { page: 1, limit: 1000 })
      ])
      this.serviceTypes = this.normalizeGroup(types, 'fuwuleixing')
      const periodData = this.normalizeGroup(periods, 'yuyueshiduan')
      const periodMap = periodData.reduce((map, item) => { map[item.name] = item.value; return map }, {})
      this.appointmentPeriods = ['上午', '下午', '晚上'].map(name => ({ name, value: periodMap[name] || 0 }))
      this.serviceStatus = this.normalizeGroup(statusRows, 'fuwuzhuangtai')
      this.reserveTypes = this.normalizeGroup(reserves, 'fuwuleixing')
      this.userStatus = this.normalizeStatusGroup(userStatus)
      this.staffStatus = this.normalizeStatusGroup(staffStatus)
      this.typeIncomeRows = this.normalizeGroup(typeIncome, 'fuwuleixing')
      this.userGender = this.normalizeGroup(userGender, 'xingbie')
      this.confirmRows = confirms && confirms.list ? confirms.list : []
      this.newsList = news && news.list ? news.list : []
      const jiesuanList = caiwujiesuan && caiwujiesuan.list ? caiwujiesuan.list : []
      this.totalTransactionAmount = jiesuanList.reduce((sum, item) => sum + Number(item.fuwufeiyong || 0), 0)
      this.platformIncome = jiesuanList.reduce((sum, item) => sum + Number(item.fuwufeiyong || 0) * (1 - Number(item.yongjinzhanbi || 0)), 0)
      const staffIncomeMap = jiesuanList.reduce((map, item) => {
        const name = item.yuangongxingming || item.name || '未分类'
        map[name] = (map[name] || 0) + Number(item.shouyi || 0)
        return map
      }, {})
      this.staffIncomeTop10 = Object.keys(staffIncomeMap).map(name => ({
        name,
        value: staffIncomeMap[name]
      })).sort((a, b) => b.value - a.value).slice(0, 10)
      this.staffIncome = this.staffIncomeTop10.reduce((sum, item) => sum + Number(item.value || 0), 0)
      this.$nextTick(this.renderCharts)
    },
    normalizeGroup(rows, nameKey) {
      return (rows || []).map(item => ({
        name: item[nameKey] || item.name || '未分类',
        value: Number(item.total || item.value || 0)
      })).filter(item => item.value >= 0).slice(0, 8)
    },
    normalizeStatusGroup(rows) {
      const result = this.normalizeGroup(rows, 'status').map(item => ({
        name: String(item.name) === '1' ? '锁定' : '正常',
        value: item.value
      }))
      return ['正常', '锁定'].map(name => ({
        name,
        value: result.filter(item => item.name === name).reduce((sum, item) => sum + Number(item.value || 0), 0)
      }))
    },
    getGroupValue(rows, names) {
      return (rows || []).filter(item => names.indexOf(item.name) > -1).reduce((sum, item) => sum + Number(item.value || 0), 0)
    },
    formatNumber(num) {
      const value = Number(num || 0)
      if (value >= 100000000) {
        return Math.floor(value / 100000000) + '亿+'
      } else if (value >= 10000) {
        return Math.floor(value / 10000) + '万+'
      }
      return value.toLocaleString('zh-CN', { maximumFractionDigits: 2 })
    },
    formatFullNumber(num) {
      return Number(num || 0).toLocaleString('zh-CN', { maximumFractionDigits: 2 })
    },
    renderCharts() {
      this.charts.forEach(chart => chart && chart.dispose())
      this.charts = []
      this.renderServiceTypeChart()
      this.renderGenderChart('userGenderChart', this.userGender, '用户')
      this.renderAppointmentPeriodChart()
      this.renderReserveTypeChart()
      this.renderTypeIncomeChart()
      this.renderStaffIncomeChart()
      this.renderQualityChart()
      this.renderAccountStatusChart()
    },
    createChart(id) {
      const el = document.getElementById(id)
      if (!el) return null
      const chart = echarts.init(el)
      this.charts.push(chart)
      return chart
    },
    emptyData(label) {
      return [{ name: label || '暂无数据', value: 1 }]
    },
    renderServiceTypeChart() {
      const chart = this.createChart('serviceTypeChart')
      if (!chart) return
      const rows = this.serviceTypes.length ? this.serviceTypes : this.emptyData('暂无服务')
      const colors = ['#5b8ff9', '#61d9a0', '#f6bd16', '#f6903d', '#e86452', '#6dc8ec', '#945fb9', '#ff99c3']
      chart.setOption({
        color: colors,
        tooltip: { trigger: 'item' },
        series: [{
          type: 'pie',
          radius: ['42%', '72%'],
          center: ['50%', '50%'],
          roseType: 'radius',
          data: rows,
          label: { formatter: '{b}\n{d}%' },
          itemStyle: { borderColor: '#fff', borderWidth: 3 }
        }]
      })
    },
    renderGenderChart(id, rows, label) {
      const chart = this.createChart(id)
      if (!chart) return
      const data = rows.length ? rows : this.emptyData('未维护')
      chart.setOption({
        color: ['#7aa7ff', '#ff6f9f', '#ffd166'],
        tooltip: { trigger: 'item' },
        title: { text: label, left: 'center', top: '42%', textStyle: { color: '#4c2d38', fontSize: 18, fontWeight: 800 } },
        series: [{ type: 'pie', radius: ['58%', '78%'], center: ['50%', '50%'], data, label: { formatter: '{b} {d}%' } }]
      })
    },
    renderAppointmentPeriodChart() {
      const chart = this.createChart('appointmentPeriodChart')
      if (!chart) return
      const rows = this.appointmentPeriods.length ? this.appointmentPeriods : this.emptyData('暂无时段')
      chart.setOption({
        color: ['#ff9ab9'],
        tooltip: { trigger: 'axis' },
        grid: { left: 42, right: 20, top: 28, bottom: 48 },
        xAxis: { type: 'category', data: rows.map(i => i.name), axisLabel: { color: '#7d4a5c', rotate: 20 } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [{ type: 'bar', barWidth: 24, data: rows.map(i => i.value), itemStyle: { borderRadius: [12, 12, 0, 0] } }]
      })
    },
    renderTypeIncomeChart() {
      const chart = this.createChart('typeIncomeChart')
      if (!chart) return
      const rows = this.typeIncomeRows.length ? this.typeIncomeRows : this.emptyData('暂无交易')
      chart.setOption({
        color: ['#ff6f9f'],
        tooltip: { trigger: 'axis' },
        grid: { left: 54, right: 20, top: 26, bottom: 58 },
        xAxis: { type: 'category', data: rows.map(i => i.name), axisLabel: { color: '#7d4a5c', rotate: 25 } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [{ type: 'line', smooth: true, symbolSize: 8, areaStyle: { opacity: 0.18 }, data: rows.map(i => i.value) }]
      })
    },
    renderStaffIncomeChart() {
      const chart = this.createChart('staffIncomeChart')
      if (!chart) return
      const rows = this.staffIncomeTop10.length ? this.staffIncomeTop10 : this.emptyData('暂无数据')
      chart.setOption({
        color: ['#5b8ff9'],
        tooltip: { trigger: 'axis', formatter: '{b}: ￥{c}' },
        grid: { left: 60, right: 20, top: 28, bottom: 48 },
        xAxis: { type: 'category', data: rows.map(i => i.name), axisLabel: { color: '#7d4a5c', rotate: 30, fontSize: 11 } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c', formatter: '￥{value}' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [{ type: 'bar', barWidth: 32, data: rows.map(i => i.value), itemStyle: { borderRadius: [8, 8, 0, 0] } }]
      })
    },
    renderReserveTypeChart() {
      const chart = this.createChart('reserveTypeChart')
      if (!chart) return
      const rows = this.reserveTypes.length ? this.reserveTypes : this.emptyData('暂无预约')
      const colors = ['#ff4f8b', '#ff9f43', '#5b8ff9', '#61d9a0', '#945fb9', '#f6bd16', '#6dc8ec', '#e86452']
      chart.setOption({
        tooltip: { trigger: 'item' },
        series: [{
          type: 'wordCloud',
          shape: 'circle',
          left: 'center',
          top: 'center',
          width: '96%',
          height: '88%',
          sizeRange: [18, 54],
          rotationRange: [-25, 25],
          rotationStep: 25,
          gridSize: 8,
          drawOutOfBound: false,
          textStyle: { color: params => colors[params.dataIndex % colors.length], fontWeight: 800 },
          emphasis: { focus: 'self', textStyle: { shadowBlur: 12, shadowColor: 'rgba(255,79,139,.35)' } },
          data: rows
        }]
      })
    },
    renderQualityChart() {
      const chart = this.createChart('qualityChart')
      if (!chart) return
      const avgScore = this.confirmRows.length ? this.confirmRows.reduce((sum, item) => sum + Number(item.pingfen || 0), 0) / this.confirmRows.length : 0
      const settled = this.confirmRows.filter(item => item.jiesuanzhuangtai === '已结算').length
      const quality = [
        this.counts.fuwuqueren,
        settled,
        Math.round(avgScore),
        this.counts.fuwuyuyue,
        this.counts.fuwudingdan
      ]
      const maxCount = Math.max(this.counts.fuwuqueren, settled, this.counts.fuwuyuyue, this.counts.fuwudingdan, 100)
      chart.setOption({
        color: ['#ff6f9f'],
        radar: { indicator: [
          { name: '确认量', max: maxCount },
          { name: '结算', max: maxCount },
          { name: '评分', max: 10 },
          { name: '预约', max: maxCount },
          { name: '订单', max: maxCount }
        ], axisName: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } }, splitArea: { areaStyle: { color: ['rgba(255,255,255,.5)', 'rgba(255,241,245,.65)'] } } },
        series: [{ type: 'radar', data: [{ value: quality, name: '服务质量' }], areaStyle: { opacity: 0.25 } }]
      })
    },
    renderAccountStatusChart() {
      const chart = this.createChart('accountStatusChart')
      if (!chart) return
      const userMap = this.statusToMap(this.userStatus)
      const staffMap = this.statusToMap(this.staffStatus)
      chart.setOption({
        color: ['#ff6f9f', '#7aa7ff'],
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        legend: { bottom: 0, textStyle: { color: '#7d4a5c' } },
        grid: { left: 42, right: 20, top: 28, bottom: 52 },
        xAxis: { type: 'category', data: ['正常', '锁定'], axisLabel: { color: '#7d4a5c' } },
        yAxis: { type: 'value', axisLabel: { color: '#7d4a5c' }, splitLine: { lineStyle: { color: '#ffe0ea' } } },
        series: [
          { name: '用户', type: 'bar', barWidth: 22, data: [userMap['正常'] || 0, userMap['锁定'] || 0], itemStyle: { borderRadius: [10, 10, 0, 0] } },
          { name: '服务人员', type: 'bar', barWidth: 22, data: [staffMap['正常'] || 0, staffMap['锁定'] || 0], itemStyle: { borderRadius: [10, 10, 0, 0] } }
        ]
      })
    },
    statusToMap(rows) {
      return (rows || []).reduce((map, item) => {
        map[item.name] = Number(item.value || 0)
        return map
      }, {})
    },
    resizeCharts() {
      this.charts.forEach(chart => chart && chart.resize())
    },
    openNews(row) {
      if (!row || row.id === 'empty') return
      this.newsDetail = row
      this.newsVisible = true
    }
  }
}
</script>

<style lang="scss" scoped>
.screen-page { min-height: calc(100vh - 84px); position: relative; overflow: hidden; padding: 26px; background: linear-gradient(135deg, #fff1f5 0%, #ffe5ee 46%, #fffaf7 100%); color: #503642;}
.screen-page:before { content: ""; position: absolute; inset: 0; background-image: radial-gradient(rgba(255,111,159,.14) 1px, transparent 1px); background-size: 18px 18px; opacity: .45; pointer-events: none; }
.screen-orb { position: absolute; border-radius: 50%; filter: blur(4px); opacity: .48; pointer-events: none; }
.orb-a { width: 420px; height: 420px; right: -150px; top: -120px; background: #ff9ab9; }
.orb-b { width: 300px; height: 300px; left: 22%; bottom: -130px; background: #ffd5a8; }
.orb-c { width: 220px; height: 220px; left: -70px; top: 32%; background: #ffcad8; }
.hero-panel, .notice-ticker, .kpi-card, .chart-card, .task-card { position: relative; background: rgba(255,255,255,.78); border: 1px solid rgba(255,111,159,.18); box-shadow: 0 20px 50px rgba(214,73,116,.14); backdrop-filter: blur(12px); }
.hero-panel { display: flex; justify-content: space-between; align-items: flex-end; padding: 34px; border-radius: 30px; overflow: hidden; }
// .hero-panel:after { content: ""; position: absolute; right: 24px; top: 22px; width: 160px; height: 160px; border: 1px solid rgba(255,111,159,.2); border-radius: 50%; animation: pulseRing 3.4s ease-in-out infinite; }
.eyebrow { color: #d94f7b; font-size: 13px; letter-spacing: 2px; text-transform: uppercase; }
.hero-copy h1 { margin: 8px 0; color: #4c2d38; font-size: 38px; letter-spacing: 1px; }
.hero-copy p { margin: 0; max-width: 760px; color: #7d4a5c; line-height: 1.7; }
.hero-side { text-align: right; color: #7d4a5c; z-index: 1; }
.today { font-size: 22px; font-weight: 800; color: #4c2d38; }
.role { margin: 8px 0 14px; }
.refresh-btn { border: 0; color: #fff; background: linear-gradient(135deg, #ff6f9f, #d94f7b); box-shadow: 0 10px 22px rgba(255,111,159,.24); }
.notice-ticker { display: flex; align-items: center; gap: 14px; margin: 18px 0 22px; padding: 12px 18px; border-radius: 18px; overflow: hidden; }
.ticker-badge { flex: 0 0 auto; padding: 8px 16px; border-radius: 999px; color: #fff; background: #ff6f9f; font-weight: 800; box-shadow: 0 10px 20px rgba(255,111,159,.26); }
.ticker-window { flex: 1; height: 36px; overflow: hidden; }
.ticker-track { animation: tickerMove 16s linear infinite; }
.ticker-window:hover .ticker-track { animation-play-state: paused; }
.ticker-item { height: 36px; display: flex; align-items: center; gap: 18px; color: #51313c; font-weight: 800; cursor: pointer; }
.ticker-item em { color: #a77586; font-style: normal; font-size: 12px; font-weight: 400; }
.ticker-spark { width: 10px; height: 10px; border-radius: 50%; background: #ffd166; box-shadow: 0 0 0 8px rgba(255,209,102,.16), 0 0 28px #ffd166; animation: spark 1.4s ease-in-out infinite; }
.kpi-grid { display: grid; grid-template-columns: repeat(6, minmax(120px, 1fr)); gap: 18px; margin-bottom: 22px; }
.kpi-card { display: flex; gap: 14px; align-items: center; padding: 20px; border-radius: 24px; transition: transform .25s, box-shadow .25s; }
.kpi-card:hover { transform: translateY(-5px) rotate(-.4deg); box-shadow: 0 26px 54px rgba(214,73,116,.18); }
.kpi-card small { margin-left: auto; color: #d94f7b; font-weight: 800; }
.kpi-icon { width: 46px; height: 46px; display: flex; align-items: center; justify-content: center; }
.kpi-icon img { width: 30px; height: 30px; object-fit: contain; flex-shrink: 0; }
.kpi-value { font-size: 26px; font-weight: 900; color: #4c2d38; }
.kpi-card.income-card .kpi-value { font-size: 21px; }
.kpi-label { color: #9b6074; font-size: 13px; }
.dashboard-grid { display: grid; gap: 18px; margin-bottom: 18px; }
.main-grid { grid-template-columns: repeat(2, 1fr); }
.sub-grid { grid-template-columns: repeat(3, 1fr); }
.bottom-grid { grid-template-columns: repeat(3, 1fr); }
.chart-card, .task-card { min-height: 330px; padding: 20px; border-radius: 26px; }
.card-head { display: flex; justify-content: space-between; align-items: baseline; margin-bottom: 12px; }
.card-head span { color: #4c2d38; font-size: 18px; font-weight: 900; }
.card-head em { color: #b37a8d; font-style: normal; font-size: 12px; }
.chart-box { width: 100%; height: 270px; }
.task-item { padding: 15px 0; border-bottom: 1px solid #ffe0ea; }
.task-item strong { display: block; color: #d94f7b; margin-bottom: 6px; }
.task-item span { color: #8b5d6e; line-height: 1.6; }
.dialog-title { width: 100%; text-align: center; font-size: 20px; color: #000; padding: 0 0 20px; font-weight: bold; }
@keyframes tickerMove { 0% { transform: translateY(0); } 100% { transform: translateY(-50%); } }
@keyframes spark { 50% { transform: scale(1.45); opacity: .55; } }
@keyframes pulseRing { 50% { transform: scale(1.12); opacity: .4; } }
@media (max-width: 1400px) { .kpi-grid { grid-template-columns: repeat(3, 1fr); } .main-grid, .sub-grid, .bottom-grid { grid-template-columns: 1fr 1fr; } }
@media (max-width: 900px) { .screen-page { padding: 16px; } .hero-panel { display: block; } .hero-side { margin-top: 18px; text-align: left; } .kpi-grid, .main-grid, .sub-grid, .bottom-grid { grid-template-columns: 1fr; } .notice-ticker { align-items: flex-start; } }
</style>
