package interpreter

import org.junit.Test
import org.junit.Assert._

import interpreter.Interpreter._

class TestsCommands {

  val memory: Memory = Map(
    Var("X") -> CstValue("xxx"),
    Var("Y") -> CstValue("yyy"),
    Var("Z") -> NlValue
  )

  @Test
  def Test_interpreterCommand_Nop(): Unit = {
    val expected = memory
    val result = interpreterCommand(Nop, memory)
    assertEquals(result, expected)

  }

  @Test
  def Test_interpreterCommand_Set1(): Unit = {
    val expected: Memory = Map(
      Var("X") -> CstValue("xxx"),
      Var("Y") -> ConsValue(CstValue("yyy"), NlValue),
      Var("Z") -> NlValue
    )
    val result =
      interpreterCommand(Set(Var("Y"), Cons(VarExp("Y"), Nl)), memory)

    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_Set2(): Unit = {
    val expected: Memory = Map(
      Var("X") -> CstValue("xxx"),
      Var("Y") -> CstValue("yyy"),
      Var("Z") -> NlValue,
      Var("W") -> ConsValue(CstValue("yyy"), NlValue)
    )
    val result =
      interpreterCommand(Set(Var("W"), Cons(VarExp("Y"), Nl)), memory)

    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommands_1(): Unit = {
    val expected: Memory = memory
    val result = interpreterCommands(List(Nop), memory)

    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommands_2(): Unit = {
    val expected: Memory = Map(
      Var("X") -> CstValue("xxx"),
      Var("Y") -> CstValue("yyy"),
      Var("Z") -> NlValue,
      Var("W") -> ConsValue(CstValue("yyy"), NlValue)
    )
    val result =
      interpreterCommands(List(Set(Var("W"), Cons(VarExp("Y"), Nl))), memory)

    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommands_3(): Unit = {
    val expected: Memory = memory
    val result = interpreterCommands(List(Nop, Nop), memory)
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommands_4(): Unit = {
    val expected: Memory = Map(
      Var("X") -> CstValue("yyy"),
      Var("Y") -> CstValue("xxx"),
      Var("Z") -> NlValue,
      Var("W") -> CstValue("xxx")
    )
    val result = interpreterCommands(
      List(
        Set(Var("W"), VarExp("X")),
        Set(Var("X"), VarExp("Y")),
        Set(Var("Y"), VarExp("W"))
      ),
      memory
    )
    assertEquals(result, expected)
  }
  
  @Test
  def Test_interpreterCommand_While_1(): Unit = {
    val memory1: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> NlValue
    )
    val result = interpreterCommand(
      While(
        VarExp("X"),
        List(
          Set(Var("Y"), Cons(Nl, VarExp("Y"))),
          Set(Var("X"), Tl(VarExp("X")))
        )
      ),
      memory1
    )
    val expected: Memory = Map(
      Var("X") -> NlValue,
      Var("Y") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue)))
    )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_While_2(): Unit = {
    val expected: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> NlValue
    )
    val result =
      interpreterCommand(
        While(
          VarExp("Y"),
          List(
            Set(Var("Y"), Cons(Nl, VarExp("Y"))),
            Set(Var("X"), Tl(VarExp("X")))
          )
        ),
        expected
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_While_3(): Unit = {
    val memory1: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> NlValue
    )
    val expected: Memory = Map(Var("X") -> NlValue, Var("Y") -> NlValue)
    val result = interpreterCommand(
      While(VarExp("X"), List(Set(Var("X"), Tl(VarExp("X"))))),
      memory1
    )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_While_4(): Unit = {
    val memory1: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val expected: Memory = Map(
      Var("X") -> NlValue,
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue)),
      Var("YY") -> NlValue,
      Var("Z") ->
        ConsValue(
          NlValue,
          ConsValue(
            NlValue,
            ConsValue(
              NlValue,
              ConsValue(
                NlValue,
                ConsValue(NlValue, ConsValue(NlValue, NlValue))
              )
            )
          )
        )
    )

    val result =
      interpreterCommand(
        While(
          VarExp("X"),
          List(
            Set(Var("YY"), VarExp("Y")),
            While(
              VarExp("YY"),
              List(
                Set(Var("Z"), Cons(Nl, VarExp("Z"))),
                Set(Var("YY"), Tl(VarExp("YY")))
              )
            ),
            Set(Var("X"), Tl(VarExp("X")))
          )
        ),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_1(): Unit = {
    val memory1: Memory = Map(Var("X") -> NlValue)
    val expected: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue)))
    )
    val result =
      interpreterCommand(
        For(
          Cons(Nl, Cons(Nl, Cons(Nl, Nl))),
          List(Set(Var("X"), Cons(Nl, VarExp("X"))))
        ),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_2(): Unit = {
    val memory1: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue)))
    )
    val expected: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue)))
    )
    val result =
      interpreterCommand(
        For(VarExp("X"), List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_3(): Unit = {
    val memory1: Memory = Map(Var("X") -> NlValue)
    val expected: Memory = Map(Var("X") -> NlValue)
    val result =
      interpreterCommand(
        For(VarExp("X"), List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_4(): Unit = {
    val memory1: Memory = Map(Var("X") -> CstValue("xxx"))
    val expected: Memory =
      Map(Var("X") -> CstValue("xxx"), Var("Y") -> ConsValue(NlValue, NlValue))
    val result =
      interpreterCommand(
        For(VarExp("X"), List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_5(): Unit = {
    val memory1: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )

    val expected: Memory = Map(
      Var("X") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue))),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue)),
      Var("Z") ->
        ConsValue(
          NlValue,
          ConsValue(
            NlValue,
            ConsValue(
              NlValue,
              ConsValue(
                NlValue,
                ConsValue(NlValue, ConsValue(NlValue, NlValue))
              )
            )
          )
        )
    )
    val result =
      interpreterCommand(
        For(
          VarExp("X"),
          List(For(VarExp("Y"), List(Set(Var("Z"), Cons(Nl, VarExp("Z"))))))
        ),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_For_6(): Unit = {
    val memory1: Memory = Map(
      Var("X") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )

    val expected: Memory = Map(
      Var("X") ->
        ConsValue(
          NlValue,
          ConsValue(
            NlValue,
            ConsValue(
              NlValue,
              ConsValue(
                NlValue,
                ConsValue(
                  NlValue,
                  ConsValue(
                    NlValue,
                    ConsValue(NlValue, ConsValue(NlValue, NlValue))
                  )
                )
              )
            )
          )
        )
    )
    val result =
      interpreterCommand(
        For(
          VarExp("X"),
          List(For(VarExp("X"), List(Set(Var("X"), Cons(Nl, VarExp("X"))))))
        ),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_If_then1(): Unit = {
    val memory1: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val expected: Memory = Map(
      Var("X") -> ConsValue(NlValue, ConsValue(NlValue, NlValue)),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val result = interpreterCommand(
      If(
        Eq(VarExp("X"), Cons(Nl, Nl)),
        List(Set(Var("X"), Cons(Nl, VarExp("X")))),
        List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))
      ),
      memory1
    )
    assertTrue(expected.equals(result))
    // assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_If_then2(): Unit = {
    val memory1: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val expected: Memory = Map(
      Var("X") -> ConsValue(NlValue, ConsValue(NlValue, NlValue)),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val result = interpreterCommand(
      If(
        Eq(VarExp("X"), VarExp("X")),
        List(Set(Var("X"), Cons(Nl, VarExp("X")))),
        List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))
      ),
      memory1
    )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_If_else1(): Unit = {
    val memory1: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val expected: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") ->
        ConsValue(NlValue, ConsValue(NlValue, ConsValue(NlValue, NlValue)))
    )
    val result =
      interpreterCommand(
        If(
          Eq(VarExp("X"), Cons(Nl, Cons(Nl, Nl))),
          List(Set(Var("X"), Cons(Nl, VarExp("X")))),
          List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))
        ),
        memory1
      )
    assertEquals(result, expected)
  }

  @Test
  def Test_interpreterCommand_If_else2(): Unit = {
    val memory1: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") -> ConsValue(NlValue, ConsValue(NlValue, NlValue))
    )
    val expected: Memory = Map(
      Var("X") -> ConsValue(NlValue, NlValue),
      Var("Y") -> ConsValue(
        NlValue,
        ConsValue(NlValue, ConsValue(NlValue, NlValue))
      )
    )
    val result =
      interpreterCommand(
        If(
          Eq(VarExp("X"), VarExp("Y")),
          List(Set(Var("X"), Cons(Nl, VarExp("X")))),
          List(Set(Var("Y"), Cons(Nl, VarExp("Y"))))
        ),
        memory1
      )
    assertEquals(result, expected)
  }
}
