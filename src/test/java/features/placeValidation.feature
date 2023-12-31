Feature: Validating Place APIS
@AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "POST" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "getPlaceAPI"

Examples:
  | name    | language | address            |
  | AAhouse | English  | World cross center |
# | BBHOUSE | Spanish | Sea cross center |

  @DeletePlace
Scenario: Verify if Delete Place functionality is working

  Given DeletePlace Payload
  When user calls "deletePlaceAPI" with "POST" http request
  Then the API call is success with status code 200
  And "status" in response body is "OK"