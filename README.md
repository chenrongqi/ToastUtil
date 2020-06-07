# ToastUtil

We know that the display of toast is controlled by the notification authority of Android system. My library is to solve the control of notification authority. If you have a better plan, welcome to exchange: chenrongqiing@163.com

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.chenrongqi:ToastUtil:1.0.1'
	}


Need to be set before the oncreate call of the first activity

        SuperToast.create(applicationContext)


Then you can use it normally

like this:

        SuperToast.makeText(info!!, Toast.LENGTH_SHORT)


and this:

            val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.BOTTOM)
            toastBuilder.setView(setToastView())
            toastBuilder.setDuration(Toast.LENGTH_LONG)
            SuperToast.makeText("自定义view toast", toastConfig = toastBuilder.buildToastStyle())
            
also can this:

    private fun setToastView(): View {
        val view = LayoutInflater.from(this).inflate(R.layout.view_toast, null)
        val recycle = view.findViewById<RecyclerView>(R.id.view_recycler)
        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle.adapter = object : RecyclerView.Adapter<ToastViewHold>() {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ToastViewHold {
                return ToastViewHold(
                    LayoutInflater.from(p0.context).inflate(R.layout.view_text, p0, false)
                )
            }

            override fun getItemCount(): Int {
                return 5
            }

            override fun onBindViewHolder(p0: ToastViewHold, p1: Int) {
                p0.itemText.append("item:$p1")
                p0.itemText.setTextColor(Color.WHITE)
            }

        }
        return view
    }

    inner class ToastViewHold(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView by lazy {
            view.findViewById<TextView>(R.id.text)
        }
    }
    
     val toastBuilder = ToastBuilder()
            toastBuilder.setGravity(Gravity.BOTTOM)
            toastBuilder.setView(setToastView())
            toastBuilder.setDuration(Toast.LENGTH_LONG)
            SuperToast.makeText("自定义view toast", toastConfig = toastBuilder.buildToastStyle())
            
            
What's important is that we are compatible with 7.0's toast crash



