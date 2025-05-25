function formatDateTime(dateTime) {
    const date = new Date(dateTime);
    if (!(date instanceof Date) || isNaN(date)) return "-";

    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    const hh = String(date.getHours()).padStart(2, '0');
    const mi = String(date.getMinutes()).padStart(2, '0');
    return `${yyyy}.${mm}.${dd} ${hh}:${mi}`;
}

function formatDate(date) {
    if (!(date instanceof Date) || isNaN(date)) return "";
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
}

function formatNumberWithComma(val) {
    if (isNaN(val)) return "-";
    return Number(val).toLocaleString();
}
