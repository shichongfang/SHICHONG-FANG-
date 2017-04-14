using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class BuildManager : MonoBehaviour {

	public TurretData laserTurretData;
	public TurretData missileTurretData;
	public TurretData standardTurretData;

	//表示当前选择的炮台(要建造的炮台)
	private TurretData selectedTurretData;

	public Text moneyText;

	public Animator moneyAnimator;

	private int money = 1000;

	void ChangeMoney(int change=0)
	{
		money += change;
		moneyText.text = "$" + money;
	}
	//表示当前选择的炮台(场景中的游戏物体)

	void Update()
	{
		if ( Input.GetMouseButtonDown(0))
		{
			if (EventSystem.current.IsPointerOverGameObject()==false)
			{
				//开发炮台的建造
				Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
				RaycastHit hit;
				bool isCollider = Physics.Raycast(ray,out hit, 1000, LayerMask.GetMask("MapCube"));
				if (isCollider)
				{
					MapCube mapCube = hit.collider.GetComponent<MapCube>();
					if (mapCube.turretGo == null)
					{
						//can create
						if(money > selectedTurretData.cost)
						{
							ChangeMoney(-selectedTurretData.cost);
							mapCube.BuildTurret(selectedTurretData.turretPrefab);
						}
						else
						{
							// not enough money
							moneyAnimator.SetTrigger("Flicker");
						}
					}
					else
					{
						//todo Update
					}

				}
			}
		}
	}

	public void OnLaserSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = laserTurretData;
		}
	}

	public void OnMissileSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = missileTurretData;
		}
	}
	public void OnStandardSelected(bool isOn)
	{
		if (isOn)
		{
			selectedTurretData = standardTurretData;
		}
	}
}
