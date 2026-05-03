import Vue from 'vue';
import VueRouter from 'vue-router'
Vue.use(VueRouter);

// 组件导入省略，保持你原来的即可
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import pay from '@/views/pay'
import register from '@/views/register'
import center from '@/views/center'
import yonghu from '@/views/modules/yonghu/list'
import fuwurenyuan from '@/views/modules/fuwurenyuan/list'
import fuwuleixing from '@/views/modules/fuwuleixing/list'
import fuwuxinxi from '@/views/modules/fuwuxinxi/list'
import fuwuyuyue from '@/views/modules/fuwuyuyue/list'
import quxiaoyuyue from '@/views/modules/quxiaoyuyue/list'
import fuwudingdan from '@/views/modules/fuwudingdan/list'
import fuwujilu from '@/views/modules/fuwujilu/list'
import fuwuqueren from '@/views/modules/fuwuqueren/list'
import caiwujiesuan from '@/views/modules/caiwujiesuan/list'
import shourumingxi from '@/views/modules/shourumingxi/list'
import news from '@/views/modules/news/list'
import chatmessage from '@/views/modules/chatmessage/list'
import friend from '@/views/modules/friend/list'
import chat from '@/views/modules/chat/list'
import chathelper from '@/views/modules/chathelper/list'
import syslog from '@/views/modules/syslog/list'
import smsregistercode from '@/views/modules/smsregistercode/list'
import users from '@/views/modules/users/list'
import menu from '@/views/modules/menu/list'
import discussfuwuxinxi from '@/views/modules/discussfuwuxinxi/list'
import config from '@/views/modules/config/list'

export const routes = [
  // 1. 根路径 = 登录页
  {
    path: '/',
    name: 'login',
    component: Login,
    meta: { icon: '', title: 'login' }
  },
  {
    path: '/register',
    name: 'register',
    component: register,
    meta: { icon: '', title: 'register' }
  },
  // 2. 后台首页 /index，所有子路由都写在这里
  {
    path: '/index',
    name: '系统首页',
    component: Index,
    children: [
      {
        path: '', // 空路径，访问 /index 时默认渲染 Home
        name: '系统首页',
        component: Home,
        meta: { icon: '', title: 'center', affix: true }
      },
      {
        path: 'updatePassword',
        name: '修改密码',
        component: UpdatePassword,
        meta: { icon: '', title: 'updatePassword' }
      },
      {
        path: 'pay',
        name: '支付',
        component: pay,
        meta: { icon: '', title: 'pay' }
      },
      {
        path: 'center',
        name: '个人信息',
        component: center,
        meta: { icon: '', title: 'center' }
      },
      // 所有后台菜单路由，去掉前面的 /，直接写路径
      { path: 'yonghu', name: '用户', component: yonghu },
      { path: 'fuwurenyuan', name: '服务人员', component: fuwurenyuan },
      { path: 'fuwuleixing', name: '服务类型', component: fuwuleixing },
      { path: 'fuwuxinxi', name: '服务信息', component: fuwuxinxi },
      { path: 'fuwuxinxistat', name: '服务信息统计', component: fuwuxinxi },
      { path: 'fuwuyuyue', name: '服务预约', component: fuwuyuyue },
      { path: 'fuwuyuyuestat', name: '服务预约统计', component: fuwuyuyue },
      { path: 'quxiaoyuyue', name: '取消预约', component: quxiaoyuyue },
      { path: 'fuwudingdan', name: '服务订单', component: fuwudingdan },
      { path: 'fuwujilu', name: '服务记录', component: fuwujilu },
      { path: 'fuwuqueren', name: '服务确认', component: fuwuqueren },
      { path: 'caiwujiesuan', name: '财务结算', component: caiwujiesuan },
      { path: 'shourumingxi', name: '收入明细', component: shourumingxi },
      { path: 'shourumingxistat', name: '收入明细统计', component: shourumingxi },
      { path: 'news', name: '通知公告', component: news },
      { path: 'chatmessage', name: '消息表', component: chatmessage },
      { path: 'friend', name: '好友表', component: friend },
      { path: 'chat', name: '在线客服', component: chat },
      { path: 'chathelper', name: '智能客服知识库', component: chathelper },
      { path: 'syslog', name: '系统日志', component: syslog },
      { path: 'smsregistercode', name: '短信验证码', component: smsregistercode },
      { path: 'users', name: '管理员', component: users },
      { path: 'menu', name: '菜单', component: menu },
      { path: 'discussfuwuxinxi', name: '服务信息评论', component: discussfuwuxinxi },
      { path: 'config/:type', name: '配置管理', component: config }
    ]
  },
  { path: '*', component: NotFound }
]

const router = new VueRouter({
  mode: 'hash',
  routes
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router;