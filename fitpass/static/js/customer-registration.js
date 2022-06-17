Vue.component("customer-registration", {
    data: function () {
        return {
            customer: { birthDate : {}},
            birth : {}
        }
    },
    template: ` 
<div>
    <h1>Registracija</h1>
	    <label>Ime: </label>
	    <input type="text" v-model = "customer.name" name="name"> <br>
	    <label>Prezime: </label>
	    <input type="text" v-model = "customer.surname" name="surname"> <br>
	    <label>Username: </label>
	    <input type="text" v-model = "customer.username" name="username"> <br>
	    <label>Password: </label>
	    <input type="password" v-model = "customer.password" name="password"> <br>
	    <label>Pol: </label>
	    <input type="radio" v-model = "customer.gender" name="gender" value="true" > <br>
	    <input type="radio" v-model = "customer.gender" name="gender" value="false" > <br>
	    <label>Datum rodjenja: </label>
	    <input type="date" v-model = "birth.b" name="birthdate"> <br>
	    <button class="login-button" v-on:click = "register">Registruj se</button>
</div>		  
`
    ,
    methods : {
        register : function () {

            const date = new Date(this.birth.b);
            const month = date.getMonth()+1;
            let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';

            const obj = JSON.parse(text);
            this.customer.birthDate = obj;
            const promise = axios.post('/users/register-customer', this.customer);
            promise.then(response => {
                if(response.data === false) {
                    alert(this.$cookie.get('isLogged'))
                    alert("Vec postoji sa tim username-om");
                } else {
                    router.push(`/login`)
                }
            })
        },
        getCookie : function (name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        }

    },

    mounted () {
    },
});