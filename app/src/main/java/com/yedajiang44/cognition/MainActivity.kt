package com.yedajiang44.cognition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.yedajiang44.cognition.Activitys.DetailActivity
import com.yedajiang44.cognition.Adapters.NavigationAdapter
import com.yedajiang44.cognition.Models.Category
import com.yedajiang44.cognition.Models.DetailItemModel
import com.yedajiang44.cognition.Models.NavigationItemModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lzh.framework.updatepluginlib.UpdateBuilder
import org.lzh.framework.updatepluginlib.UpdateConfig
import org.lzh.framework.updatepluginlib.base.UpdateParser
import org.lzh.framework.updatepluginlib.model.Update


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch {
            initUpdate()
        }
        GlobalScope.launch {
            initAdapter()
        }
    }

    /**
     * 初始化更新插件
     */
    private fun initUpdate() {
        UpdateConfig.getConfig()
            .setUrl("https://yedajiang44.com/api/update/cognition/update.json")
            .updateParser = object : UpdateParser() {
            override fun parse(response: String?): Update {
                return Gson().fromJson(response, Update::class.java)
            }
        }
        UpdateBuilder.create()
            .check()
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        val adapter = NavigationAdapter()
        adapter.setNewData(
            mutableListOf(
                NavigationItemModel(
                    Category.number,
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1580894040465&di=45393275865b19d29993bc120086f7c3&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D428945884%2C2457965726%26fm%3D214%26gp%3D0.jpg"
                ),
                NavigationItemModel(
                    Category.vegetable,
                    "https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=8a5feb414d10b912abccfeaca2949766/0e2442a7d933c895960d3481db1373f0830200dc.jpg"
                ),
                NavigationItemModel(
                    Category.fruit,
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1580750495122&di=9e29f41371e5bea67c8f58a3b93e808a&imgtype=0&src=http%3A%2F%2Fimg03.sogoucdn.com%2Fapp%2Fa%2F200716%2Ff202f2bd6a5a12c32ca72454d82ce8a9"
                )
            )
        )
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(this, DetailActivity::class.java)
            when (adapter.data[position].category) {
                Category.number -> {
                    val items = mutableListOf<DetailItemModel>()
                    for (index in 0..100) {
                        items.add(DetailItemModel(index.toString(), ""))
                    }
                    val json = Gson().toJson(items)
                    intent.putExtra("data", json)
                }
                Category.vegetable -> {
                    val json = readJsonLocalFile(this, "vegetable.json")
                    intent.putExtra("data", json)
                }
                Category.fruit -> {
                    val json = readJsonLocalFile(this, "fruit.json")
                    intent.putExtra("data", json)
                }
            }
            startActivity(intent)
        }
        viewPager.adapter = adapter
    }

    private fun readJsonLocalFile(context: Context, path: String): String {
        val stringBuilder = StringBuilder()
        context.assets.open(path)
        val inputStream = context.assets.open(path)
        val buffer = ByteArray(1024)
        var index = -1
        while ({ index = inputStream.read(buffer);index }() > 0) {
            stringBuilder.append(String(buffer.copyOfRange(0, index)))
        }
        inputStream.close()
        return stringBuilder.toString()
    }
}