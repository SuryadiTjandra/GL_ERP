export function extractPath(object, path){
	const paths= path.split(".");
	let obj = object;
	
	for (const p of paths){
		obj = obj[p];
	}
	return obj;
}