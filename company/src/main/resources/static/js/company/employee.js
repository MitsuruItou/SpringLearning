/**
 *
 */

function onClickCreate() {

	var $form = $('#newEmployee');

	var data = $form.serializeArray();
	data = parseJson(data);

	$.ajax({
		url : $form.attr("action"),
		type : $form.attr("method"),
		contentType : "application/json",
		data : JSON.stringify(data),
	}).done(function (data) {
		location.href = "./";
	}).fail(function (message){
		alert(message);
	});
}
//
//function onClickUpdate() {
//
//	var $form = $('#editEmployee');
//
//	var data = $form.serializeArray();
//
//	if (data[1].value ==="") {
//		alert("名前を入力してください");
//	} else {
//		data = parseJson(data);
//
//		$.ajax({
//			url : $form.attr("action"),
//			type : $form.attr("method"),
//			contentType : "application/json",
//			data : JSON.stringify(data),
//		}).done(function (data) {
//			location.href = "./";
//		}).fail(function (message){
//			alert(message);
//		});
//	}
//}

function parseJson(data) {
	var returnJson = {};
	for(idx = 0; idx < data.length; idx++) {
		returnJson[data[idx].name] = data[idx].value;
	}

	return returnJson;
}