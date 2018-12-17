class AJAXPerformer{
	constructor(){
		
	}
	
	static async getAsJson(url, requestParamObject={}, withCsrf=false){
		let headers = new Headers();
		if (withCsrf){
			const csrfHeader = document.getElementsByName("_csrf_header")[0].getAttribute("content");
			const csrfToken = document.getElementsByName("_csrf")[0].getAttribute("content");
			headers.append(csrfHeader, csrfToken);
			headers.append('Content-Type', 'application/json');
		}
		
		let paramStr = Object.keys(requestParamObject).map(key => key + '=' + param[key]).join('&');
		if (paramStr.length > 0){
			if (!url.endsWith("?"))
				url = url + "?"
			url = url + paramStr;
		}
		
		let res = await fetch(url, {
			method: "GET",
			headers: headers
		})
		return res.json();
	}
}

export default AJAXPerformer;