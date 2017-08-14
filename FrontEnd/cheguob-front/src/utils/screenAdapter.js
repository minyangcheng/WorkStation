/**
 * Created by cheguo on 2017/3/28.
 */
export default function () {
    const screenWidth = window.screen.width;
    const radio = (screenWidth/375);
    const suitPercent = (radio*0.625).toFixed(3)*100+'%';
    // 动态写入样式
    document.getElementsByTagName('html')[0].style.fontSize = suitPercent;
    // 给js调用的，某一dpr下rem和px之间的转换函数
    window.rem2px = function(v) {
        v = parseFloat(v);
        return v * 10;
    };
    window.px2rem = function(v) {
        v = parseFloat(v);
        return v / 10;
    };
    window.rem = 10;
}
