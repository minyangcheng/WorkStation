/**
 * Created by cheguo on 2017/5/26.
 */
String.prototype.format = String.prototype.f = function () {
  let s = this
  let i = arguments.length

  while (i--) {
    s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i])
  }
  return s
}

export default {

}
