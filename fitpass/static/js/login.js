Vue.component("login", {
    data: function () {
        return {
            login: {},
        }
    },
    template: ` 
<div>
    <h1>Login</h1>
    <div class="login-container"
    <div class="login-box">
	    <label>Username: </label>
	    <input type="text" v-model = "login.username" name="username"> <br>
	    <label>Password: </label>
	    <input type="password" v-model = "login.password" name="password"> <br>
	    <button v-on:click = "tryLogin" class="login-button">Login</button>
	</div>
</div>		  
`
    ,
    methods: {
        tryLogin: function () {
            const promise = axios.post('/users/login', this.login);
            promise.then(response => {
                if (response.data === false) {
                    alert("Pogresan login");
                    toast("asdasdasd");
                } else {
                    $cookies.set("isLogged", true, 10000)
                    router.push(`/`)
                }
            })
        },

    },

    mounted() {
    },
});