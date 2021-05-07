package com.depromeet.zerowaste.data.home

//TODO :: 타입 정해진거 제외하고 일단 전부 String 타입으로 해놓았음
data class Mission(
    val id: String = "",
    val name: String = "",
    val createrId: String = "",
    val numberOfLikes: String = "",
    val category: String = "",
    val reportedCounts: Int = 0,
    val difficulty: String = "",
    val logo: String = "",
    val iconImg: String = "",
    val content: String = "",
    val startDate: String = "",
    val endDate: String = "",
)
/*
- id (pk)
- name
- creater_id (user_id로 구분됨)
- ~~number_of_likes → return 될때 이 데이터는 따로 추가하고, 디비에 기록할 필요는 없음~~
- Category
- reported_counts : Integer
- difficulty
- logo
- icon_img
- content
- start_date → 미션 시작날짜와 끝 날짜
- end_date
*/
