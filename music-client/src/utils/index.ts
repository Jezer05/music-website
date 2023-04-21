export function attachUrl(url:string) {
  // 这里因配置proxy，所以不用也不能加前缀
  return `http:/api/${url}`
}

export function formatSeconds(value:number) {
  value = Math.floor(value);
  let minute = 0;
  let second = value;
  if (value > 60) {
    minute = Math.floor(value / 60);
    second = value % 60;
  }
  let minute_str = "";
  let second_str = "";
  if (minute < 10)
    minute_str = "0" + minute;
  else minute_str = "" + minute;
  if (second < 10)
    second_str = "0" + second;
  else second_str = "" + second
  return minute_str + ":" + second_str;
}
// 解析日期
export function parseTime(cellValue: any) {
  if (cellValue == null || cellValue == "") return "";
  const date = new Date(cellValue);
  const year = date.getFullYear();
  const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
  const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
  return year + "-" + month + "-" + day;
}
// 获取歌曲名
export  function getSongTitle(str : string) {
  return str.split("-")[1];
}

// 获取歌手名
export  function getSingerName(str : string) {
  return str.split("-")[0];
}