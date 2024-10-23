<!--
 * @Description: 
 * @Author: 
 * @Date: 2024-02-21 18:13:21
-->
<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      if (icon.includes('el-icon')) {
        vnodes.push(<i class={[icon, 'sub-el-icon']}  style='font-size:24px;' />)
      } else if(icon.includes('icon-r')) {
        
        vnodes.push(<i class={['iconfont', icon]} style='font-size:24px;' />)
      } else {
        vnodes.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      vnodes.push(<span slot='title'> {(title)}</span>)
    }
    return vnodes
  }
}
</script>

<style scoped>
.sub-el-icon,.iconfont {
  color: currentColor;
  width: 1em;
  height: 1em;
  font-size: 24px;
  margin-right: 10px;
}
</style>
