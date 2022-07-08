Vue.component("subscription", {
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
            subOffer: {
                type: "",
                price: "",
                dailyTrainings: "",
                username: "",
                promoCode: "",
            },
            showSubs: true,
            currentSubscription: null,
            searchedPromoCode: "",
            validPromoCode: null,
            discountedPrice: "",

        }
    },
    template: ` 
<div>

<div v-if="currentSubscription != null" name="showCurrentSubscription">
    <h3>Current subscription:</h3>
    <table>
    <tr><th>ID</th><th>TYPE</th><th>START DATE</th><th>END DATE</th><th>DAILY TRAININGS AVAILABLE</th></tr>
    <tr><td>{{currentSubscription.id}}</td><td>{{currentSubscription.type}}</td>
    <td>{{currentSubscription.payDate.year}} - {{currentSubscription.payDate.month}} - {{currentSubscription.payDate.day}}</td>
    <td>{{currentSubscription.validUntil.year}} - {{currentSubscription.validUntil.month}} - {{currentSubscription.validUntil.day}}</td>
    <td>{{currentSubscription.dailyEnteringNumber}}</td></tr>
    </table>
</div>
<div v-else name="noCurrentSubscription">
<h3>User has no current subscription</h3>
</div>

<div v-if="showSubs" name="Subs">
    <table>
    <tr>
    <th>Type</th>
    <th>Price</th>
    <th>Daily Trainings</th>
    <th>ORDER</th>
    </tr>
    
    <tr>
    <td>MONTHLY</td>
    <td>2000</td>
    <td>1</td>
    <td><button v-on:click="order(0)">order this</button></td>
    </tr>
    
    <tr>
    <td>MONTHLY</td>
    <td>3000</td>
    <td>3</td>
    <td><button v-on:click="order(1)">order this</button></td>
    </tr>
    
    <tr>
    <td>YEARLY</td>
    <td>20000</td>
    <td>3</td>
    <td><button v-on:click="order(2)">order this</button></td>
    </tr>
    
    <tr>
    <td>YEARLY</td>
    <td>30000</td>
    <td>10</td>
    <td><button v-on:click="order(3)">order this</button></td>
    </tr>
    </table>
</div>

<div v-else name="checkout">
<table>
    <tr><td><b>Type:</b></td><td>{{this.subOffer.type}}</td></tr>
    <tr><td><b>Price:</b></td><td>{{this.subOffer.price}}</td></tr>
    <tr><td><b>Daily Trainings:</b></td><td>{{this.subOffer.dailyTrainings}}</td></tr>
    <tr><td><b>Promo Code</b></td><td><input v-model="searchedPromoCode" v-on:input="checkPromoCode"></td><p v-if="validPromoCode"></p></tr>
    <tr><td><b>Final Price</b></td><td>{{this.discountedPrice}}</td></tr>
    <tr><td><button v-on:click="reset">Cancel</button></td><td><button v-on:click="orderFinish">FINISH ORDER</button></td></tr>

</table>
</div>
<button v-on:click="conslog">LOG</button>

</div>		  
`
    ,
    methods:
        {
            checkPromoCode() {
                axios.get('/users/check-promo-code?src=' + this.searchedPromoCode, this.configHeaders)
                    .then(response => {
                        this.validPromoCode = response.data
                        if (this.validPromoCode != null) {
                            this.calculateDiscountedPrice();
                        } else {
                            this.discountedPrice = this.subOffer.price;
                        }


                    })

            },

            calculateDiscountedPrice() {
                var price = this.subOffer.price;
                price = price - price * this.validPromoCode.discount / 100;
                this.discountedPrice = price;
            },


            orderFinish() {
                if(this.validPromoCode){
                    this.subOffer.promoCode = this.validPromoCode.code
                }
                axios.post('/users/create-subscription', this.subOffer, this.configHeaders);
                router.push('/');
            },

            reset() {
                this.subOffer.type = "";
                this.subOffer.price = "";
                this.subOffer.dailyTrainings = "";
                this.searchedPromoCode = "";
                this.showSubs = true;
                this.validPromoCode = null;
            },

            conslog() {
                console.log(this.subOffer.type);
                console.log(this.subOffer.price);
                console.log(this.subOffer.dailyTrainings);
                console.log(this.subOffer.username);
                console.log(this.configHeaders.headers.token);
                console.log(this.validPromoCode);
                this.calculateDiscountedPrice()


            },
            order(id) {
                if (id === 0) {
                    this.subOffer.type = "MONTHLY";
                    this.subOffer.price = 2000;
                    this.subOffer.dailyTrainings = "1";
                } else if (id === 1) {
                    this.subOffer.type = "MONTHLY";
                    this.subOffer.price = 3000;
                    this.subOffer.dailyTrainings = "3";
                } else if (id === 2) {
                    this.subOffer.type = "YEARLY";
                    this.subOffer.price = 20000;
                    this.subOffer.dailyTrainings = "3";
                } else if (id === 3) {
                    this.subOffer.type = "YEARLY";
                    this.subOffer.price = 30000;
                    this.subOffer.dailyTrainings = "10";
                }
                this.showSubs = false;
                this.discountedPrice = this.subOffer.price;
            }

        },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                this.subOffer.username = this.userInfo.username
                axios.get('users/get-subscription?username=' + this.subOffer.username, this.configHeaders)
                    .then(response => {
                        this.currentSubscription = response.data
                    })
                if (this.userInfo.role !== 'CUSTOMER') {
                    router.push("/")
                }
            })


    },
});