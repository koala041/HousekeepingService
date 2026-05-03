const base = {
	fileUrl(path) {
		if (!path) return ''
		let url = String(path).split('?')[0].trim()
		if (!url) return ''
		url = url.replace(/\\/g, '/')
		const uploadIndex = url.indexOf('/upload/')
		if (uploadIndex >= 0) {
			url = url.substring(uploadIndex + 1)
		}
		if (/^https?:\/\//i.test(url)) return url
		if (!url.startsWith('upload/')) {
			url = 'upload/' + url.replace(/^\/+/, '')
		}
		return this.get().url + url
	},
	get() {
		return {
			url : "/springcloud-alibaba/",
			name: "springcloud-alibaba",
			// 退出到首页链接
			indexUrl: '/springcloud-alibaba/front/h5/index.html'
		};
	},
	getProjectName(){
		return {
			projectName: "家政服务系统"
		} 
	},
	getUsersName(){
		return {
			usersname: '管理员'
		}
	},
	getBgName() {
		return {
			bgNameList: [
				{
					name: '后台登录页',
					value: 'bLoginBackgroundImg'
				},
				{
					name: '后台注册页',
					value: 'bRegisterBackgroundImg'
				},
				{
					name: '后台首页',
					value: 'bIndexBackgroundImg'
				},
				{
					name: '后台头部LOGO',
					value: 'bTopLogo'
				},
				{
					name: '后台首页LOGO',
					value: 'bHomeLogo'
				},
				{
					name: '前台登录页',
					value: 'fLoginBackgroundImg'
				},
				{
					name: '前台注册页',
					value: 'fRegisterBackgroudImg'
				},
				{
					name: '前台头部LOGO',
					value: 'fTopLogo'
				},
				{
					name: 'APP登录页',
					value: 'appLoginBackgroundImg'
				},
				{
					name: 'APP注册页',
					value: 'appRegisterBackgroudImg'
				},
				{
					name: 'APP登录页LOGO',
					value: 'appLoginLogo'
				},
				{
					name: 'APP注册页LOGO',
					value: 'appRegLogo'
				},
			]
		}
	}
}
export default base
