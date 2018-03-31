var gulp = require('gulp');

//定义一个读写index.js的任务
gulp.task('dealIndex',function(){
  //读取Index文件
  gulp.src('js/index.js')
  //通过管道操作这个流
  //将文件内容放到dest文件夹内
    .pipe(gulp.dest('dest'))

});

gulp.task('default', function () {
  gulp.src('page/js/*.*')
    .pipe(gulp.dest('page_'));
});

gulp.watch('js/*.js',function(e){
  console.log(e)
  //将js下面的js文件放入demo文件夹下
  gulp.src('js/*.js')
    .pipe(gulp.dest('demo'))
});

// gulp.watch('js/*.js',['dealJs','demo'])

