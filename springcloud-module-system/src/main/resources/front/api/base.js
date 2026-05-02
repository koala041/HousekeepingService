const base = {
    url : "http://localhost:8080/springcloud-alibaba/",
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
		return this.url + url
	}
}
export default base
