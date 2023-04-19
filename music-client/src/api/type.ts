export interface LoginReqForm {
  username: string;
  password: string;
}

export interface MusicEntity{
  id:number,
  name:string,
  pic:string,
  url:string,
  lyric?:string
}

export interface PlaylistDetail{
  id:number,
  title:string,
  pic:string,
  introduction:string
  style: string
}
