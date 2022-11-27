[地址](https://www.jianshu.com/p/5612e83e99e7)
#使用方式
```
private fun showList() {
        val stickHeader = binding.shcHeader
        val tvHeader = stickHeader.findViewById<TextView>(R.id.tv_title)
        val tvHeaderExt = stickHeader.findViewById<TextView>(R.id.tv_title_ext)
        stickHeader.setDataCallback(object : DataCallback{
            override fun onDataChange(pos: Int) {
                Log.d(TAG, "onDataChange: pos=$pos")
                currentPos = pos
                val item = mAdapter.getItem(pos)
                tvHeader.text = item.itemText
                if (item.isShowExt) {
                    tvHeaderExt.visibility = View.VISIBLE
                    tvHeaderExt.text = item.itemTextExt
                }else {
                    tvHeaderExt.visibility = View.INVISIBLE
                }

            }
        })

        tvHeaderExt.setOnClickListener {
            Toast.makeText(this@ListFloatItemActivity, "点击了Header Ext", Toast.LENGTH_SHORT).show()
        }

        val stickyDecoration = StickyItemDecoration(stickHeader, MultiListAdapter.ITEM_TYPE_TITLE)
        stickyDecoration.setOnStickyChangeListener(object : OnStickyChangeListener{
            override fun onScrollable(offset: Int) {
                Log.d(TAG, "onScrollable: offset=$offset")
                stickHeader.scrollChild(offset)
                stickHeader.visibility = View.VISIBLE
            }

            override fun onInVisible() {
                stickHeader.reset()
                stickHeader.visibility = View.INVISIBLE
            }

        })
        binding.rvList.addItemDecoration(stickyDecoration)

        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = MultiListAdapter(mList)
        binding.rvList.adapter = mAdapter
    }
```