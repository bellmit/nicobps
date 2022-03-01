if(window.location.href.match(/(style)|(alert)|(script)|(javascript)/gmi)){
	window.location=window.location.href.replace(/(\/?style)|(alert)|(\/?script)|(javascript)|(\/?iframe)|(eval)|(expression)|(vbscript)|(onload)/g, "");
}