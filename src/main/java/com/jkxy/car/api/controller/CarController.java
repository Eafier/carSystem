package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    /**
     * 通过车名和型号购车
     * 每辆车在数据库中为一条数据，车名和型号共同决定买车类型
     *
     * @param carName
     * @param carType
     * @return
     */
    @GetMapping("buyCar/{carName}/{carType}/{number}")
    public JSONResult findByCarName(@PathVariable String carName, @PathVariable String carType, @PathVariable Integer number) {
        //查询某品牌型号车的list
        List<Car> cars = carService.findByCarNameAndType(carName, carType);
        //是否满足购买数量
        if (number > cars.size()) {
            return JSONResult.ok("购车数超出剩余数量");
        }
        //删除被购买车辆数据
        for (int i = 0; i < number; i++) {
            carService.deleteById(cars.get(i).getId());
        }
        return JSONResult.ok("购车成功");
    }

    /**
     * 模糊查询,获取范围数据
     *
     */
    @GetMapping("findFromTo/{first}/{second}")
    public JSONResult findFromTo(@PathVariable Integer first, @PathVariable Integer second) {
        //传参要求
        if (first < 0 || first > second) {
            return JSONResult.ok("参数非法");
        }
        List<Car> cars = carService.findAll();
        List<Car> range = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            if (i >= first-1 && i <= second-1) {
                range.add(cars.get(i));
            }
        }
        return JSONResult.ok(range);
    }

}
