var gulp = require('gulp'),
    sass = require('gulp-sass'),
    map   = require('map-stream'),
    sourcemaps = require('gulp-sourcemaps'),
    watch = require('gulp-watch'),
    autoprefixer = require('gulp-autoprefixer')
    fest = require('gulp-fest');

gulp.task('sass', function () {
    gulp.src('public_html/css/scss/main.scss')
        .pipe(sass())
        .pipe(autoprefixer({
            browsers: ['last 3 versions'],
            cascade: false
        }))
        .pipe(sourcemaps.write())
        .pipe(gulp.dest('public_html/css/main.css'));
});

gulp.task('sass-prod', function () {

});

gulp.task('fest', function () {
    gulp.src('templates/**/*.xml')
    .pipe(fest())
    .pipe(gulp.dest('public_html/js/tmpl/'));
});

gulp.task('watch', function () {
    gulp.watch('public_html/css/**/*.scss', ['sass']);
    gulp.watch('templates/**/*.xml', ['fest']);
});

gulp.task('default', ['watch'], function () {
    console.log('Running tasks...');
});

gulp.task('build', ['fest', 'sass-prod'])
