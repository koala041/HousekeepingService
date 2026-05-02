import axios from 'axios'
import router from '@/router/router-static'
import storage from '@/utils/storage'
import {Message} from 'element-ui'

const uploadFilePattern = /\.(jpg|jpeg|png|gif|bmp|webp|svg|mp4|mp3|wav|ogg|pdf|doc|docx|xls|xlsx|ppt|pptx)(\?.*)?$/i

function normalizeUploadPath(value) {
	if (typeof value !== 'string' || !value) return value
	if (value.indexOf('<') >= 0 || value.indexOf('>') >= 0) return value
	return value.split(',').map(item => {
		let url = item.trim().replace(/\\/g, '/')
		if (!url) return url
		const uploadIndex = url.indexOf('/upload/')
		if (uploadIndex >= 0) {
			return url.substring(uploadIndex + 1)
		}
		if (/^https?:\/\//i.test(url) || url.startsWith('data:')) return url
		if (url.startsWith('upload/')) return url
		return uploadFilePattern.test(url) ? 'upload/' + url.replace(/^\/+/, '') : url
	}).join(',')
}

function normalizeResponseFiles(data) {
	if (Array.isArray(data)) {
		data.forEach(normalizeResponseFiles)
		return data
	}
	if (data && typeof data === 'object') {
		Object.keys(data).forEach(key => {
			const value = data[key]
			data[key] = typeof value === 'string' ? normalizeUploadPath(value) : normalizeResponseFiles(value)
		})
	}
	return data
}

const http = axios.create({
	timeout: 1000 * 86400,
	withCredentials: true,
	baseURL: '/springcloud-alibaba',
	headers: {
		'Content-Type': 'application/json; charset=utf-8'
	}
})
// 请求拦截
http.interceptors.request.use(config => {
	config.headers['Token'] = storage.get('Token') // 请求头带上token
	return config
}, error => {
	return Promise.reject(error)
})
// 响应拦截
http.interceptors.response.use(response => {
	normalizeResponseFiles(response.data)
	if (response.data && response.data.code === 401) { // 401, token失效
		// Message.error('请先登录')
		setTimeout(()=>{
			router.push({ name: 'login' })
		},1000)
	}
	return response
}, error => {
	return Promise.reject(error)
})
export default http
