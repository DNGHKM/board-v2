// 공통 쿼리스트링 추출
const pathParts = location.pathname.split('/');
const boardEngName = pathParts[2]; // /boards/free/list → "free"

// 특정 URL에 현재 쿼리스트링을 붙여주는 유틸
function withQueryString(url) {
    return `${url}${location.search}`;
}
function listWithQueryString() {
    return `/boards/${boardEngName}/list${location.search}`;
}

// 파라미터 상태 추출 함수
function getSearchParams() {
    const urlParams = new URLSearchParams(location.search);

    const today = new Date();
    const endDate = urlParams.get("endDate") || formatDate(today);

    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(today.getFullYear() - 1);
    const startDate = urlParams.get("startDate") || formatDate(oneYearAgo);

    return {
        boardEngName: boardEngName,
        page: urlParams.get("page") || 1,
        size: urlParams.get("size") || 10,
        categoryName: urlParams.get("categoryName") || "",
        keyword: urlParams.get("keyword") || "",
        startDate: startDate,
        endDate: endDate
    };
}

// 로컬 기준 날짜 문자열 반환
function formatDate(date) {
    return [
        date.getFullYear(),
        String(date.getMonth() + 1).padStart(2, '0'),
        String(date.getDate()).padStart(2, '0')
    ].join('-');
}
