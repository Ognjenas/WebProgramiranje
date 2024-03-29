Vue.component("reserve-offer", {
    data: function () {

        return {
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }

            },
            id: "",
            userInfo: {
                username: "",
                role: ""
            },
            offer : "",
            reserveOffer: {date: {}, time: "", offerId: ""},
            dateValue: "",
            times : "",
            actualPrice:"",
            customerType:"",

        }
    },
    template: ` 
<div>
<div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
    </div>
    <div v-if="$cookies.get('token') == null">
        <button v-on:click="login">Login</button>
    </div>
   <div class="facility-list-container">
   <div class="show-facilities-table">
   <h1>Reserve</h1>
    <div v-if="userInfo.role == 'CUSTOMER'">
        <input v-model="dateValue" type="date" v-on:change="getAvailableTimes">
         <select v-model="reserveOffer.time">
          <option v-for="time in times" :value="time">{{time}}</option>
        </select>
    </div>
    <div>
        <p>Name: {{offer.name}}</p>
        <p>Type: {{offer.type}}</p>
        <p>Duration: {{offer.duration}}</p>
        <p>Price: {{actualPrice}}</p>
        <button class="login-button" v-if="userInfo.role == 'CUSTOMER'" v-on:click="reserveButton()">Reserve</button>
    </div>
    </div>
    </div>
</div>		  
`
    ,
    methods:
        {
            login() {
                router.push('/login')
            },
            editProfile() {
                router.push("/edit-profile")
            },
            getAvailableTimes() {
                const date = new Date(this.dateValue);
                const month = date.getMonth()+1;
                let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
                const obj = JSON.parse(text);
                axios
                    .post('/show-facility/get-offer/get-available-appointments?id='+this.offer.id, obj, this.configHeaders)
                    .then(response => this.times = response.data.times)
            },
            reserveButton() {
                if(this.userInfo.role === 'CUSTOMER') {
                    const date = new Date(this.dateValue);
                    const month = date.getMonth() + 1;
                    let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
                    const obj = JSON.parse(text);
                    this.reserveOffer.date = obj;
                    this.reserveOffer.offerId = this.offer.id;
                    axios
                        .post('/customer/make-appointment', this.reserveOffer, this.configHeaders)
                        .then(response => {
                            if (response.data === false) {
                                alert("Nije dobro")
                            } else {
                                router.push("/")
                            }
                        })
                }
            }
        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                })
        }

        axios
            .get('/show-facility/get-offer?id=' + this.$route.params.id)
            .then(response => {
                this.offer = response.data;
                axios.get('customer/get-customer-type',this.configHeaders)
                    .then(response=>{
                        this.customerType = response.data;
                        this.actualPrice=this.offer.price-this.offer.price*this.customerType.discount/100;
                    })
            })
    },
});