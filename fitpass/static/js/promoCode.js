Vue.component("promo-code", {
    data: function () {
        return {
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            userInfo: {
                username: "",
                role: ""
            },
            promoCode:{
                code:"",
                discount:"",
                endDate:"",
                usageTimes:"",
            },
            date:"",
            validPromoCodes:null,
        }
    },
    template: ` 
<div>

<form>
<div class="facility-list-container">
<div v-if="validPromoCodes == null">
<h3>Currently no available PromoCodes</h3>
</div>
<div v-else>
<h3>Available PromoCodes:</h3>
<table class="show-facilities-table">
    <tr>
        <th>CODE</th>
        <th>DISCOUNT</th>
        <th>START DATE</th>
        <th>END DATE</th>
        <th>REMAINING USES</th>
    </tr>
    
    <tr v-for="p in validPromoCodes">
        <td>{{p.code}}</td>
		<td>{{p.discount}}</td>
		<td>{{p.startDate.year}} - {{p.startDate.month}} - {{p.startDate.day}}</td>
		<td>{{p.endDate.year}} - {{p.endDate.month}} - {{p.endDate.day}}</td>
		<td>{{p.usageTimes}}</td>
    </tr>
</table>
</div>
<h3>Make new PromoCode:</h3>
<table class="show-facilities-table">
    <tr><td><b>PROMO CODE</b></td><td><input v-model="promoCode.code"></td></tr>
    <tr><td><b>PERCENT DISCOUNT</b></td><td><input v-model="promoCode.discount">%</td></tr>
    <tr><td><b>USAGE TIMES</b></td><td><input v-model="promoCode.usageTimes"></td></tr>
    <tr><td><b>VALID UNTIL</b></td><td><input type="date" v-model="date" name="birthdate"></td></tr>
    <tr><td><button class="login-button" type="reset" v-on:click="reset">RESET</button></td><td><button class="login-button" type="submit" v-on:click="create">CREATE</button></td></tr>
</table>
</form>
</div>
</div>  
`
    ,
    methods: {

        log(){
          console.log(this.validPromoCodes);
        },

        create(){
            const date = new Date(this.date);
            const month = date.getMonth()+1;
            let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
            const obj = JSON.parse(text);
            this.promoCode.endDate = obj;
            axios.post('/administrator/create-promo-code',this.promoCode,this.configHeaders)
        },

        reset(){
            this.promoCode.code="";
            this.promoCode.discount="";
            this.promoCode.usageTimes="";
            this.date="";
        },

    },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                if (this.userInfo.role !== 'ADMINISTRATOR') {
                    router.push("/")
                }
            })
        axios.get('/administrator/get-promo-codes',this.configHeaders)
            .then(response =>{
                this.validPromoCodes=response.data.allPromoCodes;
            })

    },
});