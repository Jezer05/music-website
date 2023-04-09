export interface LoginReqForm {
  username: string;
  password: string;
}

export interface SingerReqForm{
  name: string,
  sex: number,
  birth: Date,
  location: string,
  introduction: string,
}

export interface SongReqForm{
  name:string,
  singerId: number | null,
  introduction: string,
  lyric: string,
}
