# 多媒体工具类

#### 使用演示类 [MediaUse](https://github.com/afkT/DevUtils/blob/master/app/src/main/java/utils_use/media/MediaUse.java) 介绍了配置参数及使用

#### 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player)

* 多媒体管理类（[DevMediaManager](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevMediaManager.java)）：MediaPlayer 统一管理类, 全局使用一个 MediaPlayer

* 视频播放控制类（[DevVideoPlayerControl](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevVideoPlayerControl.java)）：视频播放控制器, 快捷播放视频工具类


#### 框架亮点

* 单例 MediaPlayer, 全局统一管理, 防止多个多媒体资源同时存在后台播放

* 快捷封装各种通用方法, 以及监听事件处理, 控制处理 MediaPlayer

* 支持使用 MediaSet 抽象类设置 MediaPlayer 其他配置方法等, 以及是否循环播放等

* 支持快捷播放 raw、assets、本地SDCard、http 等路径, 多媒体文件


## API 文档

* **MediaPlayer 统一管理类 ->** [DevMediaManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevMediaManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 DevMediaManager 实例 |
| setAudioStreamType | 设置流类型 |
| playPrepareRaw | 播放 Raw 资源 |
| playPrepareAssets | 播放 Assets 资源 |
| playPrepare | 预加载播放 - (file-path or http/rtsp URL) http 资源、本地资源 |
| isPlaying | 是否播放中 |
| pause | 暂停操作 |
| stop | 停止操作 - 销毁 MediaPlayer |
| isIgnoreWhat | 是否忽略错误类型 |
| onError | 播放出错回调 |
| onVideoSizeChanged | 视频大小改变通知 |
| onPrepared | 使用 mMediaPlayer.prepareAsync() 异步播放准备成功回调 |
| onCompletion | 视频播放结束回调 |
| onBufferingUpdate | MediaPlayer 缓冲更新回调 |
| onSeekComplete | 滑动加载完成回调 |
| setMediaListener | 设置 MediaPlayer 回调事件 |
| isNullMediaPlayer | 判断 MediaPlayer 是否为 null |
| isNotNullMediaPlayer | 判断 MediaPlayer 是否不为 null |
| getMediaPlayer | 获取 MediaPlayer 对象 |
| setMediaPlayer | 设置 MediaPlayer 对象 |
| setTAG | 设置日志打印 TAG |
| getVolume | 获取播放音量 |
| setVolume | 设置播放音量 |
| getPlayRawId | 获取播放资源 id |
| getPlayUri | 获取播放地址 |
| getVideoWidth | 获取视频宽度 |
| getVideoHeight | 获取视频高度 |
| getCurrentPosition | 获取播放时间 |
| getDuration | 获取资源总时间 |
| getPlayPercent | 获取播放进度百分比 |
| isLooping | 是否循环播放 - 默认不循环 |
| setMediaConfig | 设置播放配置 |


* **视频播放控制器 ->** [DevVideoPlayerControl.java](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/player/DevVideoPlayerControl.java)

| 方法 | 注释 |
| :- | :- |
| surfaceChanged | Surface 改变通知 |
| surfaceCreated | Surface 创建 |
| surfaceDestroyed | Surface 销毁 |
| onPrepared | 准备完成回调 |
| onCompletion | 播放完成/结束 |
| onBufferingUpdate | 缓存进度 |
| onSeekComplete | 滑动进度加载成功 |
| onError | 异常回调 |
| onVideoSizeChanged | 视频大小改变通知 |
| setMediaListener | 设置播放监听事件 |
| pausePlayer | 暂停播放 |
| stopPlayer | 停止播放 |
| startPlayer | 开始播放 |
| getSurfaceView | 获取 SurfaceView |
| isPlaying | 是否播放中 |
| isAutoPlay | 判断是否自动播放 |
| setAutoPlay | 设置自动播放 |
| getPlayUri | 获取播放地址 |
| getVideoWidth | 获取视频宽度 |
| getVideoHeight | 获取视频高度 |
| getCurrentPosition | 获取播放时间 |
| getDuration | 获取资源总时间 |
| getPlayPercent | 获取播放进度百分比 |


#### 使用方法
```java
// 设置 TAG, 打印日志使用
DevMediaManager.getInstance().setTAG(TAG);
// 设置音量
DevMediaManager.getInstance().setVolume(50);
// 设置流类型
DevMediaManager.getInstance().setAudioStreamType(AudioManager.STREAM_MUSIC);

// 获取播放音量
DevMediaManager.getInstance().getVolume();
// 获取当前播放的地址
DevMediaManager.getInstance().getPlayUri();
// 获取播放的资源id
DevMediaManager.getInstance().getPlayRawId();
// 获取 当前播放时间
DevMediaManager.getInstance().getCurrentPosition();
// 获取资源总时间
DevMediaManager.getInstance().getDuration();
// 获取播放进度百分比
DevMediaManager.getInstance().getPlayPercent();
// 获取 MediaPlayer 对象
DevMediaManager.getInstance().getMediaPlayer();

// 获取播放的视频高度
DevMediaManager.getInstance().getVideoHeight();
// 获取播放的视频宽度
DevMediaManager.getInstance().getVideoWidth();

// 是否播放中
DevMediaManager.getInstance().isPlaying();
// 停止操作
DevMediaManager.getInstance().stop();
// 暂停操作
DevMediaManager.getInstance().pause();

// 设置事件监听
DevMediaManager.getInstance().setMediaListener(new DevMediaManager.MediaListener() {
    @Override
    public void onPrepared() {
        if (DevMediaManager.getInstance().isNotNullMediaPlayer()) {
            // 播放操作
            DevMediaManager.getInstance().getMediaPlayer().start();
        }
    }

    @Override
    public void onCompletion() {
    }

    @Override
    public void onBufferingUpdate(int percent) {
    }

    @Override
    public void onSeekComplete() {
    }

    @Override
    public void onError(int what, int extra) {
    }

    @Override
    public void onVideoSizeChanged(int width, int height) {
    }
});

// =

// 播放音频
DevMediaManager.getInstance().playPrepareRaw(R.raw.dev_beep);
DevMediaManager.getInstance().playPrepareAssets("a.mp3");
DevMediaManager.getInstance().playPrepare(PathUtils.getSDCard().getSDCardPath() + "/a.mp3");
DevMediaManager.getInstance().playPrepare("http://xxx.mp3");
DevMediaManager.getInstance().playPrepare(new DevMediaManager.MediaSet() {
    @Override
    public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
        mediaPlayer.setDataSource("xxx");
    }
}); // 自由设置信息

// =

SurfaceView surfaceView = null;
// 播放视频
DevVideoPlayerControl control = new DevVideoPlayerControl(surfaceView);
control.startPlayer(PathUtils.getSDCard().getSDCardPath() + "/video_3.mp4");
control.startPlayer("http://xxx.mp4");
control.startPlayer(new DevMediaManager.MediaSet() {
    @Override
    public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
        mediaPlayer.setDataSource("xxx");
    }
}); // 自由设置信息
```