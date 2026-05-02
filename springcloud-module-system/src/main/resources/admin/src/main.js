import Vue from 'vue'
import App from '@/App.vue'
import store from './store'
// element ui 完全引入
import ElementUI from 'element-ui'
import '@/assets/css/element-variables.scss'
import '@/assets/css/style.scss'
// 加载路由
// import router from '@/router/router-static.js';
import router from '@/router/router-static.js';
// 面包屑导航，注册为全局组件
import BreadCrumbs from '@/components/common/BreadCrumbs'
Vue.component('bread-crumbs', BreadCrumbs)
// 引入echart
import * as echarts from 'echarts'
import 'echarts-wordcloud'
// 引入echart主题
// import  '@/assets/js/echarts-theme-macarons.js'
import 'echarts/theme/macarons.js'
// ajax
import http from '@/utils/http.js'
// 基础配置
import base from '@/utils/base'
// 工具类
import { 
	isAuth,
	getCurDate,
	getCurDateTime,
} from '@/utils/utils'
// storage 封装
import storage from "@/utils/storage";
// 上传组件
import FileUpload from "@/components/common/FileUpload";
Vue.component('file-upload', FileUpload)

// 富文本编辑组件
import Editor from "@/components/common/Editor";
Vue.component('editor', Editor)
// api 接口
import api from '@/utils/api'
// 数据校验工具类
import * as validate from '@/utils/validate.js'
// excel导出
import JsonExcel from 'vue-json-excel'
Vue.component('downloadExcel', JsonExcel)
Vue.prototype.$getBgName = base.getBgName()
Vue.prototype.$getUsersName = base.getUsersName()
Vue.prototype.$validate = validate
Vue.prototype.$http = http // ajax请求方法
Vue.prototype.$echarts = echarts
Vue.prototype.$base = base.get()
Vue.prototype.$fileUrl = path => base.fileUrl(path)
Vue.prototype.$project = base.getProjectName()
Vue.prototype.$storage = storage
Vue.prototype.$api = api
// 判断权限方法
Vue.prototype.isAuth = isAuth
Vue.prototype.getCurDateTime = getCurDateTime
Vue.prototype.getCurDate = getCurDate
// Vue.prototype.$base = base
Vue.use(ElementUI, { size: 'medium', zIndex: 3000 });
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
