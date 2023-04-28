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

export function parseLyric(text:string) {
  let lines = text.split("\n");
  const pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
  const result = [];

  // 对于歌词格式不对的特殊处理
  if (!/\[.+\]/.test(text)) {
    return [[0, text]];
  }

  while (!pattern.test(lines[0])) {
    lines = lines.slice(1);
  }

  lines[lines.length - 1].length === 0 && lines.pop();
  for (const item of lines) {
    const time = item.match(pattern); // 存前面的时间段
    const value = item.replace(pattern, ""); // 存歌词
    for (const item1 of time) {
      const t = item1.slice(1, -1).split(":");
      if (value !== "") {
        result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]);
      }
    }
  }
  result.sort((a, b) => a[0] - b[0]);
  return result;
}