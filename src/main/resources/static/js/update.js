// (1) 회원정보 수정
function update(userId) {
	event.preventDefault(); // 폼태그 액션을 막기(더이상 진행되지 않도록)
	
	let data = $("#profileUpdate").serialize(); //폼태그에 있는 key=value 전송시 사용
	
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data:data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
	}).done(res=>{//HttpStatus 상태코드 200번대
		console.log("update 성공", res);
		location.href=`/user/${userId}`;
	}).fail(error=>{//HttpStatus 상태코드 200번대가 아닐 떄 에러
		if(error.data==null){
			alert(error.responseJSON.message);
		}else{			
		alert(JSON.stringify(error.responseJSON.data));//자바스크립트문자열을 json문자열로 변경 후 알럿
		}
	});
}