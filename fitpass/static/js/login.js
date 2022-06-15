Vue.component("login", {
    data: function () {
        return {
            login: { },
        }
    },
    template: ` 
<div>
    <h1>Login</h1>
	    <label>Username: </label>
	    <input type="text" v-model = "customer.username" name="username"> <br>
	    <label>Password: </label>
	    <input type="password" v-model = "customer.password" name="password"> <br>
	    <button v-on:click = "login">
</div>		  
`
    ,
    methods : {
        login : function () {
            const promise = axios.post('/users/login', this.login);
            promise.then(response => {
                if(response.data === false) {
                    alert("Pogresan login");
                } else {

                    router.push(`/`)
                }
            })
        },

    },

    mounted () {
    },
});