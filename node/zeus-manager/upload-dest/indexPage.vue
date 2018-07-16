<template>
    <scroller >
        <common-line/>
        <!--<mds-cell v-for="(item) in items"-->
                  <!--:title="item.title"-->
                  <!--:has-arrow="true"-->
                  <!--@mdsCellClicked="clickItem(item)"></mds-cell>-->
        <text>1111</text>
        <mds-cell title="11111"
                  :has-arrow="true"
                  @mdsCellClicked="clickItem(item)"></mds-cell>
    </scroller>
</template>
<script>
    import 'Config';
    import {MdsCell} from 'mds-ui';
    import {commonLine} from 'Components/view';

    export default {
        components: {commonLine, MdsCell},
        data() {
            return {
                items: [{title: '车辆销售', page: ''},]
            }
        },
        created() {
        },
        methods: {
            clickItem(item) {
                this.$router.open({name: item.page});
            }
        }
    }
</script>
<style lang="sass" scoped>
</style>
