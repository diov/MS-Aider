package io.github.diov.msaider

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SerializableUnitTest {

    @Test
    fun json_isCorrect() {
        val json = """
{"wanted_list":[{"quest_info":{"quest_name":"憤怒×和×希望","subquest_name":"究極","article_url":"https://gamewith.tw/monsterstrike/article/show/86230","icon_url":"https://s3-ap-northeast-1.amazonaws.com/gamewith-tw/article_tools/monst/gacha/3009.jpg","category":"降臨"},"conditions":{"tags":["僅限極運"]},"congestion":2,"recruitment_number":15,"participation_number":13}],"filter_conditions":{"quest_categories":["超絶・爆絶・轟絶","降臨","イベント","亀クエ・タス","獣神竜","英雄の神殿","曜日限定","チケット","玉楼","神獣の聖域","閃きの遊技場","禁忌の獄"],"tags":["僅限極運","僅限適正角色","誰都可以"]}}
        """.trimIndent()
        val parseResult = Json(JsonConfiguration.Stable).parse(OrderBoard.serializer(), json)
        val result =
            RecruitResult(
                1,
                "https://static.tw.monster-strike.com/sns/?pass_code=MjQzMTgyMjU1\u0026f=line",
                "intent://joingame/?join=MjQzMTgyMjU1#Intent;scheme=monsterstrike-tw-app;end",
                "",
                90
            )
        assertNotEquals(result, parseResult)
    }
}
