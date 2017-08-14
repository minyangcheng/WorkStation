/**
 * Created by cheguo on 2017/4/20.
 */
let env=process.env;
export let API_SERVER_URL=env.API_SERVER_URL;
export let SOURCE=env.SOURCE;
export let SECRET=env.SECRET;
export let DEBUG=env.NODE_ENV==='development';

export default env;
