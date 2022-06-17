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
	    <button v-on:click = "register">
</div>		  
`
    ,
    methods : {
        register : function () {
            var date = new Date(this.birth.b)
            let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + date.getMonth() + ', "day" : ' + date.getDay() + ' }';

            const obj = JSON.parse(text);
            this.customer.birthDate = obj;
            const promise = axios.post('/users/register-customer', this.customer);
            promise.then(response => {
                if(response.data === false) {
                    alert("Vec postoji sa tim username-om");
                } else {
                    router.push(`/`)
                }
            })
        }
    },

    mounted () {
    },
});