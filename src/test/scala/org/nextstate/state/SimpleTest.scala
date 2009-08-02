package org.nextstate.state

import com.jteigen.scalatest.JUnit4Runner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

@RunWith(classOf[JUnit4Runner])
class SimpleTest extends Spec with ShouldMatchers {
  describe("Demo"){
    it("should run"){
      1 + 1 should be (2)
    }

    ignore("should ignore"){
      1 + 1 should be (3) // doesn't fail as the test is ignored
    }

    ignore("should crash and burn!"){
      1 + 1 should be (3)
    }
  }
}    
